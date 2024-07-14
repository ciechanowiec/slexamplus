package eu.ciechanowiec.slexamplus;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceTypeServletTest extends TestEnvironment {

    ResourceTypeServletTest() {
        super(ResourceResolverType.RESOURCERESOLVER_MOCK);
    }

    @Test
    void mustHaveRed() {
        ResourceTypeServlet resourceTypeServlet = context.registerInjectActivateService(ResourceTypeServlet.class);
        String propertyName = "textOnPage";
        String expectedText = "This is text on a page";
        context.build()
               .resource("/content/simple-page", Map.of(propertyName, expectedText))
               .commit();
        context.currentResource("/content/simple-page");
        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();
        resourceTypeServlet.doGet(request, response);
        String outputAsString = response.getOutputAsString();
        boolean hasRed = outputAsString.contains("red");
        assertTrue(hasRed);
    }
}
