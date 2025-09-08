package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.test.TestEnvironment;
import lombok.SneakyThrows;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DedicatedRRServletTest extends TestEnvironment {

    DedicatedRRServletTest() {
        super(ResourceResolverType.RESOURCERESOLVER_MOCK);
    }

    @SneakyThrows
    @Test
    void mustDoGet() {
        context.registerInjectActivateService(Counter.class);
        DedicatedRRServlet servlet = context.registerInjectActivateService(DedicatedRRServlet.class);
        MockSlingJakartaHttpServletRequest request = context.jakartaRequest();
        MockSlingJakartaHttpServletResponse response = context.jakartaResponse();
        servlet.doGet(request, response);
        response.reset();
        servlet.doGet(request, response);
        String actualOutput = response.getOutputAsString();
        assertEquals("Counter: 2", actualOutput);
    }
}
