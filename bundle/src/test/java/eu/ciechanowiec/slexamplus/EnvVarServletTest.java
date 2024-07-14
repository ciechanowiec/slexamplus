package eu.ciechanowiec.slexamplus;

import lombok.SneakyThrows;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
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
        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();
        envVarServlet.doGet(request, response);
        String outputAsString = response.getOutputAsString();
        assertEquals("Environment variable is set to 'envus-variablus'", outputAsString);
    }
}
