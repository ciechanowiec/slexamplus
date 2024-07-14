package eu.ciechanowiec.slexamplus;

import lombok.SneakyThrows;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;

import javax.jcr.Session;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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
                (Answer<ResourceResolver>) invocation -> resourceAccess.acquireAccess()
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

    @SneakyThrows
    private void exportJCRtoXML() {
        @SuppressWarnings("resource")
        ResourceResolver resolver = resourceAccess.acquireAccess();
        Session session = Optional.ofNullable(resolver.adaptTo(Session.class)).orElseThrow();
        Path path = Paths.get("repo.xml");
        OutputStream out = Files.newOutputStream(path);
        session.exportDocumentView("/", out, true, false);
    }
}
