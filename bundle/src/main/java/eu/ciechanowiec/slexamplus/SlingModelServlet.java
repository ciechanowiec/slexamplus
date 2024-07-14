package eu.ciechanowiec.slexamplus;

import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.Writer;
import java.util.Optional;

@Component(
        service = {Servlet.class, SlingModelServlet.class},
        immediate = true
)
@ServiceDescription("Provides an HTTP response with the use of a Sling Model")
@SlingServletPaths("/sling-model")
@SuppressWarnings("squid:S1948")
@Slf4j
public class SlingModelServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        try (ResourceResolver resourceResolver = request.getResourceResolver();
             Writer responseWriter = response.getWriter()) {
            Resource resource = Optional.ofNullable(resourceResolver.getResource("/apps/slexamplus/application"))
                    .orElseThrow();
            UsualModel usualModel = Optional.ofNullable(resource.adaptTo(UsualModel.class))
                    .orElseThrow();
            RecordModel recordModel = Optional.ofNullable(resource.adaptTo(RecordModel.class))
                    .orElseThrow();
            String usualString = usualModel.toString();
            String recordString = recordModel.toString();
            String finalString = String.format("%s<br>%s", usualString, recordString);
            response.setContentType(MediaType.TEXT_HTML);
            responseWriter.write(finalString);
        }
    }
}
