package eu.ciechanowiec.slexamplus.models;

import eu.ciechanowiec.slexamplus.bundled.AAProvider;
import eu.ciechanowiec.slexamplus.bundled.BBProvider;
import eu.ciechanowiec.slexamplus.bundled.FactoryProvider;
import eu.ciechanowiec.slexamplus.bundled.StringProvidersCluster;
import eu.ciechanowiec.sling.rocket.test.TestEnvironment;
import org.apache.jackrabbit.vault.util.JcrConstants;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletRequest;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BooksModelTest extends TestEnvironment {

    BooksModelTest() {
        super(ResourceResolverType.RESOURCERESOLVER_MOCK);
    }

    @Test
    void mustCreateModel() {
        context.build().resource("/content", Map.of("customProperty", "customusPropertius")).commit();
        context.registerInjectActivateService(AAProvider.class);
        context.registerInjectActivateService(BBProvider.class);
        context.registerInjectActivateService(FactoryProvider.class, Map.of("provided-string", "FACTORIUS_1"));
        context.registerInjectActivateService(FactoryProvider.class, Map.of("provided-string", "FACTORIUS_2"));
        context.registerInjectActivateService(StringProvidersCluster.class);
        context.currentResource("/content");
        MockSlingJakartaHttpServletRequest request = context.jakartaRequest();
        BooksModel booksModel = Optional.ofNullable(request.adaptTo(BooksModel.class)).orElseThrow();
        assertAll(
            () -> assertEquals(JcrConstants.NT_UNSTRUCTURED, booksModel.getPrimaryType()),
            () -> assertEquals("customusPropertius", booksModel.getCustomProperty()),
            () -> assertEquals("Default value for a non-existent property", booksModel.getNonExistentProperty()),
            () -> assertEquals("/content", booksModel.getResource().getPath()),
            () -> assertNotNull(booksModel.getRequest()),
            () -> assertEquals("AA###BB###FACTORIUS_1###FACTORIUS_2", booksModel.getOsgiService().combinedStrings())
        );
    }
}
