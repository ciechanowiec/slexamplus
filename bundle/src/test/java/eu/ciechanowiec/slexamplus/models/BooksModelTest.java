package eu.ciechanowiec.slexamplus.models;

import eu.ciechanowiec.sling.rocket.test.TestEnvironment;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BooksModelTest extends TestEnvironment {

    BooksModelTest() {
        super(ResourceResolverType.RESOURCERESOLVER_MOCK);
    }

    @Test
    void mustGetNames() {
        context.build().resource("/content").commit();
        try (ResourceResolver resourceResolver = context.resourceResolver()) {
            BooksModel booksModel = Optional.ofNullable(resourceResolver.getResource("/content"))
                    .flatMap(resource -> Optional.ofNullable(resource.adaptTo(BooksModel.class)))
                    .orElseThrow();
            assertTrue(booksModel.getNames().startsWith("I'm the text from the Sling Model: "));
        }
    }
}
