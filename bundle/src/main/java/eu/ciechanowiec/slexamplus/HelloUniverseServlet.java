package eu.ciechanowiec.slexamplus;

import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.Writer;

@Component(
        service = {Servlet.class, HelloUniverseServlet.class},
        immediate = true
)
@ServiceDescription("Shows a welcome message at a given path")
@SlingServletPaths("/hello-universe")
@SuppressWarnings("squid:S1948")
@Slf4j
public class HelloUniverseServlet extends SlingSafeMethodsServlet {

    private final BasicHTMLPage basicHTMLPage;

    @Activate
    public HelloUniverseServlet(
            @Reference(cardinality = ReferenceCardinality.MANDATORY)
            BasicHTMLPage basicHTMLPage
    ) {
        log.info("Servlet initialized");
        this.basicHTMLPage = basicHTMLPage;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        RequestPathInfo requestPathInfo = request.getRequestPathInfo();
        log.info("Received request: {}", requestPathInfo);
        String html = basicHTMLPage.producePage();
        response.setContentType(MediaType.TEXT_HTML);
        try (Writer responseWriter = response.getWriter()) {
            responseWriter.write(html);
        }
        log.info("Finished writing response");
    }
}
