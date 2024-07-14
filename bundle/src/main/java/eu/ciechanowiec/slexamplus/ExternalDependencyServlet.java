package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.conditional.Conditional;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

@Component(
        service = {Servlet.class, ExternalDependencyServlet.class},
        immediate = true
)
@ServiceDescription("Provides an HTTP response with the use of an external library")
@SlingServletPaths("/external-dependency")
@SuppressWarnings("squid:S1948")
@Slf4j
public class ExternalDependencyServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(
            @SuppressWarnings("NullableProblems") SlingHttpServletRequest request, SlingHttpServletResponse response
    ) throws IOException {
        String originalText = "I'm the text provided with the use of an external library";
        String extractedText = Conditional.conditional(true)
                                          .onTrue(() -> originalText)
                                          .get(String.class);
        response.setContentType(MediaType.TEXT_HTML);
        try (Writer responseWriter = response.getWriter()) {
            responseWriter.write(Objects.requireNonNull(extractedText));
        }
    }
}
