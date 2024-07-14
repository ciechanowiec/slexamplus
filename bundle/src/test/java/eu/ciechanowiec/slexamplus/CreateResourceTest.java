package eu.ciechanowiec.slexamplus;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.junit5.SlingContext;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SlingContextExtension.class)
@SuppressWarnings("PMD.CloseResource")
class CreateResourceTest {

    private final SlingContext context = new SlingContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    @Test
    @SuppressWarnings("resource")
    void mustCreateResource() {
        String propertyName = "textOnPage";
        String expectedText = "This is text on a page";
        context.build()
               .resource("/content/simple-page", Map.of(propertyName, expectedText))
               .commit();
        ResourceResolver resourceResolver = context.resourceResolver();
        Resource resource = Optional.ofNullable(resourceResolver.getResource("/content/simple-page")).orElseThrow();
        ValueMap valueMap = resource.getValueMap();
        String actualText = valueMap.get(propertyName, String.class);
        assertEquals(expectedText, actualText);
    }
}
