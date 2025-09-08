package eu.ciechanowiec.slexamplus.bundled;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.junit5.SlingContext;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingJakartaHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Annotation;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SlingContextExtension.class)
@SuppressWarnings("TypeName")
class StringProvidersClusterTest {

    private final SlingContext context = new SlingContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
    private StringProvidersClusterServlet servlet;

    @BeforeEach
    void setup() {
        context.registerInjectActivateService(new BBProvider());
        context.registerInjectActivateService(new AAProvider());
        context.registerInjectActivateService(new FactoryProvider(), Map.of("provided-string", "FACTORIUS_1"));
        context.registerInjectActivateService(new FactoryProvider(), Map.of("provided-string", "FACTORIUS_2"));
        StringProvidersCluster cluster = new StringProvidersCluster(clusterConfig("==="));
        cluster.configure(clusterConfig("***"));
        context.registerInjectActivateService(cluster);
        servlet = context.registerInjectActivateService(StringProvidersClusterServlet.class);
    }

    @Test
    void mustMatchStrings() {
        MockSlingJakartaHttpServletRequest request = context.jakartaRequest();
        MockSlingJakartaHttpServletResponse response = context.jakartaResponse();
        servlet.doGet(request, response);
        String actualString = response.getOutputAsString();
        String expectedString = "AA***BB***FACTORIUS_1***FACTORIUS_2";
        assertEquals(expectedString, actualString);
    }

    private StringProvidersClusterConfig clusterConfig(String delimiter) {
        return new StringProvidersClusterConfig() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return StringProvidersClusterConfig.class;
            }

            @Override
            public String delimiter() {
                return delimiter;
            }
        };
    }
}
