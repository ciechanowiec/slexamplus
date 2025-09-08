package eu.ciechanowiec.slexamplus;

import jakarta.ws.rs.core.MediaType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingJakartaHttpServletRequest;
import org.apache.sling.api.SlingJakartaHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingJakartaSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import jakarta.servlet.Servlet;
import java.io.Writer;

@Component(
        service = {ResourceTypeServlet.class, Servlet.class},
        immediate = true
)
@ServiceDescription("Outputs the resource as a red string")
@SuppressWarnings("squid:S1948")
@Slf4j
@SlingServletResourceTypes(
        methods = HttpConstants.METHOD_GET,
        extensions = "red",
        resourceTypes = ServletResolverConstants.DEFAULT_RESOURCE_TYPE
)
public class ResourceTypeServlet extends SlingJakartaSafeMethodsServlet {

    @SneakyThrows
    @Override
    protected void doGet(
            SlingJakartaHttpServletRequest request, SlingJakartaHttpServletResponse response
    ) {
        Resource resource = request.getResource();
        String resourceString = resource.toString();
        String output = String.format("<p style=\"color: red;\">%s</p>", resourceString);
        response.setContentType(MediaType.TEXT_HTML);
        try (Writer responseWriter = response.getWriter()) {
            responseWriter.write(output);
        }
    }
}
