package eu.ciechanowiec.slexamplus;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SlingContextExtension.class)
@SuppressWarnings("PMD.CloseResource")
class ModelTest {

    private final SlingContext context = new SlingContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    @Test
    @SuppressWarnings("LineLength")
    void mustLoadResource() throws IOException {
        Class<ModelTest> testClass = ModelTest.class;
        try (InputStream testData = testClass.getResourceAsStream("BasicJCRData.json")) {
            Objects.requireNonNull(testData);
            ContentLoader contentLoader = context.load();
            contentLoader.json(testData, "/content");
        }
        @SuppressWarnings("resource")
        ResourceResolver resourceResolver = context.resourceResolver();
        Resource resource = Optional.ofNullable(resourceResolver.getResource("/content")).orElseThrow();
        UsualModel usualModel = Optional.ofNullable(resource.adaptTo(UsualModel.class))
                .orElseThrow();
        RecordModel recordModel = Optional.ofNullable(resource.adaptTo(RecordModel.class))
                .orElseThrow();
        String actualUsualProperty = usualModel.getCustomProperty();
        String actualRecordProperty = recordModel.customProperty();
        String actualUsualString = usualModel.toString();
        String actualRecordString = recordModel.toString();
        String exectedUsualString = "UsualModel(primaryType=sling:Folder, customProperty=Customus Propertius, nonExistentProperty=Default value for a non-existent property)";
        String expectedRecordString = "RecordModel[primaryType=sling:Folder, customProperty=Customus Propertius, nonExistentProperty=Default value for a non-existent property]";
        assertAll(
                () -> assertEquals("Customus Propertius", actualUsualProperty),
                () -> assertEquals("Customus Propertius", actualRecordProperty),
                () -> assertEquals(exectedUsualString, actualUsualString),
                () -> assertEquals(expectedRecordString, actualRecordString)
        );
    }
}
