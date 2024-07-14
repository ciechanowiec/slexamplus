package eu.ciechanowiec.slexamplus;

import lombok.SneakyThrows;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.junit5.SlingContext;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SlingContextExtension.class)
class ExternalDependencyServletTest {

    private final SlingContext context = new SlingContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    @SneakyThrows
    @Test
    void mustWriteToResponse() {
        ExternalDependencyServlet servlet = context.registerInjectActivateService(new ExternalDependencyServlet());
        MockSlingHttpServletResponse response = context.response();
        MockSlingHttpServletRequest request = context.request();
        servlet.doGet(request, response);
        String expectedOutput = "I'm the text provided with the use of an external library";
        String actualOutput = response.getOutputAsString();
        assertEquals(expectedOutput, actualOutput);
    }
}
