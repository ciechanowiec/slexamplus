package eu.ciechanowiec.slexamplus;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.junit5.SlingContext;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.apache.sling.testing.mock.sling.loader.ContentLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SlingContextExtension.class)
@SuppressWarnings("PMD.CloseResource")
class LoadResourceTest {

    private final SlingContext context = new SlingContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    @Test
    void mustLoadResource() throws IOException {
        Class<LoadResourceTest> testClass = LoadResourceTest.class;
        try (InputStream testData = testClass.getResourceAsStream("BasicJCRData.json")) {
            Objects.requireNonNull(testData);
            ContentLoader contentLoader = context.load();
            contentLoader.json(testData, "/content");
        }
        @SuppressWarnings("resource")
        ResourceResolver resourceResolver = context.resourceResolver();
        Resource resource = Optional.ofNullable(resourceResolver.getResource("/content")).orElseThrow();
        ValueMap props = resource.getValueMap();
        String actualValue = props.get("customProperty", String.class);
        assertEquals("Customus Propertius", actualValue);
    }
}
