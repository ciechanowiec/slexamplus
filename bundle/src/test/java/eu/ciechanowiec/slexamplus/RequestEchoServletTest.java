package eu.ciechanowiec.slexamplus;

import lombok.SneakyThrows;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.junit5.SlingContext;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SlingContextExtension.class)
class RequestEchoServletTest {

    private final SlingContext context = new SlingContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    @SneakyThrows
    @Test
    void mustDescribeRequestOnGet() {
        RequestEchoServlet servlet = context.registerInjectActivateService(RequestEchoServlet.class);
        String resourcePath = "/content/echoed-page";
        context.build()
               .resource(resourcePath, Map.of("propertyName", "propertyValue"))
               .commit();
        context.currentResource(resourcePath);
        MockSlingJakartaHttpServletRequest request = context.jakartaRequest();
        MockSlingJakartaHttpServletResponse response = context.jakartaResponse();
        servlet.doGet(request, response);
        String actualOutput = response.getOutputAsString();
        assertAll(
            () -> assertTrue(actualOutput.contains(resourcePath)),
            () -> assertTrue(actualOutput.contains("method"))
        );
    }

    @SneakyThrows
    @Test
    void mustEchoParametersOnPost() {
        String resourcePath = "/content/echoed-page";
        context.build()
               .resource(resourcePath)
               .commit();
        context.currentResource(resourcePath);
        MockSlingJakartaHttpServletRequest request = context.jakartaRequest();
        request.addRequestParameter("firstParam", "unusValue");
        request.addRequestParameter("secondParam", "duoValue");
        MockSlingJakartaHttpServletResponse response = context.jakartaResponse();
        RequestEchoServlet servlet = context.registerInjectActivateService(RequestEchoServlet.class);
        servlet.doPost(request, response);
        String actualOutput = response.getOutputAsString();
        assertAll(
            () -> assertTrue(actualOutput.contains("firstParam")),
            () -> assertTrue(actualOutput.contains("unusValue")),
            () -> assertTrue(actualOutput.contains("secondParam")),
            () -> assertTrue(actualOutput.contains("duoValue"))
        );
    }
}
