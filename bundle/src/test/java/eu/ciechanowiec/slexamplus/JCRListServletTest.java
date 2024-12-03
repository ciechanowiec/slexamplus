package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.test.TestEnvironment;
import lombok.SneakyThrows;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(SlingContextExtension.class)
@SuppressWarnings("PMD.CloseResource")
class JCRListServletTest extends TestEnvironment {

    JCRListServletTest() {
        super(ResourceResolverType.JCR_OAK);
    }

    @SneakyThrows
    @Test
    void mustListJCRRoot() {
        JCRListServlet jcrListServlet = context.registerInjectActivateService(JCRListServlet.class);
        MockSlingHttpServletRequest request = spy(context.request());
        MockSlingHttpServletResponse response = context.response();
        when(request.getResourceResolver()).thenAnswer(
                (Answer<ResourceResolver>) invocation -> fullResourceAccess.acquireAccess()
        );
        jcrListServlet.doGet(request, response);
        String actualString = response.getOutputAsString();
        String expectedString = """
                                /rep:security
                                /jcr:system
                                /oak:index""";
        exportJCRtoXML();
        assertEquals(expectedString, actualString);
    }
}
