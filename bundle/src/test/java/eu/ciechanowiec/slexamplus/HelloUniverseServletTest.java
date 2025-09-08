package eu.ciechanowiec.slexamplus;

import lombok.SneakyThrows;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.junit5.SlingContext;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SlingContextExtension.class)
class HelloUniverseServletTest {

    private final SlingContext context = new SlingContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    @SneakyThrows
    @Test
    void mustSayHello() {
        context.registerInjectActivateService(BasicHTMLPage.class);
        HelloUniverseServlet servlet = context.registerInjectActivateService(HelloUniverseServlet.class);
        MockSlingJakartaHttpServletRequest request = context.jakartaRequest();
        MockSlingJakartaHttpServletResponse response = context.jakartaResponse();
        servlet.doGet(request, response);
        String actualOutput = response.getOutputAsString();
        String actualOutputLowerCase = actualOutput.toLowerCase(Locale.ENGLISH);
        boolean hasHello = actualOutputLowerCase.contains("hello");
        assertTrue(hasHello);
    }
}
