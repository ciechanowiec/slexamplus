package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.commons.ResourceAccess;
import lombok.SneakyThrows;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.context.SlingContextImpl;
import org.apache.sling.testing.mock.sling.junit5.SlingContext;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({
        "NewClassNamingConvention", "ProtectedField", "WeakerAccess", "AbstractClassName",
        "VisibilityModifier", "PMD.AvoidAccessibilityAlteration", "PMD.AbstractClassWithoutAbstractMethod"
})
@ExtendWith({SlingContextExtension.class, MockitoExtension.class})

public abstract class TestEnvironment {

    protected final SlingContext context;
    protected final ResourceAccess resourceAccess;

    @SuppressWarnings("resource")
    public TestEnvironment(ResourceResolverType resourceResolverType) {
        context = new SlingContext(resourceResolverType);
        context.resourceResolver(); // trigger RR initialization
        resourceAccess = mock(ResourceAccess.class);
        when(resourceAccess.acquireAccess()).thenAnswer(
                (Answer<ResourceResolver>) invocation -> getFreshAdminRR()
        );
        context.registerService(ResourceAccess.class, resourceAccess);
    }

    @SneakyThrows
    private ResourceResolver getFreshAdminRR() {
        Class<SlingContextImpl> slingContextClass = SlingContextImpl.class;
        Field resourceResolverFactoryField = slingContextClass.getDeclaredField("resourceResolverFactory");
        resourceResolverFactoryField.setAccessible(true);
        ResourceResolverFactory resourceResolverFactory =
                (ResourceResolverFactory) resourceResolverFactoryField.get(context);
        @SuppressWarnings("deprecation")
        ResourceResolver resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
        return resourceResolver;
    }
}
