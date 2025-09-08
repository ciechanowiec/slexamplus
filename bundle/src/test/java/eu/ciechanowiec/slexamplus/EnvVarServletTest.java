package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.test.TestEnvironment;
import lombok.SneakyThrows;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvVarServletTest extends TestEnvironment {

    EnvVarServletTest() {
        super(ResourceResolverType.RESOURCERESOLVER_MOCK);
    }

    @SneakyThrows
    @Test
    void mustDoGet() {
        EnvVarServlet envVarServlet = context.registerInjectActivateService(
                EnvVarServlet.class, Map.of("env-variable", "envus-variablus")
        );
        MockSlingJakartaHttpServletRequest request = context.jakartaRequest();
        MockSlingJakartaHttpServletResponse response = context.jakartaResponse();
        envVarServlet.doGet(request, response);
        String outputAsString = response.getOutputAsString();
        assertEquals("Environment variable is set to 'envus-variablus'", outputAsString);
    }
}
