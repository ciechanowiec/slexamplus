package eu.ciechanowiec.slexamplus;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingJakartaHttpServletRequest;
import org.apache.sling.api.SlingJakartaHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingJakartaSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import jakarta.servlet.Servlet;
import java.io.Writer;
import java.util.Optional;
import java.util.StringJoiner;

@Component(
        service = {JCRListServlet.class, Servlet.class},
        immediate = true
)
@ServiceDescription("Lists nodes on the first level of the JCR repository with the use of JCR API")
@SlingServletPaths("/jcr-list")
@SuppressWarnings("squid:S1948")
@Slf4j
public class JCRListServlet extends SlingJakartaSafeMethodsServlet {

    @SneakyThrows
    @Override
    protected void doGet(
            @SuppressWarnings("NullableProblems") SlingJakartaHttpServletRequest request,
            @SuppressWarnings("NullableProblems") SlingJakartaHttpServletResponse response
    ) {
        log.info("Listing nodes on the first level of the JCR repository");
        StringJoiner childrenPaths = new StringJoiner("\n");
        try (ResourceResolver resourceResolver = request.getResourceResolver()) {
            Session session = Optional.ofNullable(resourceResolver.adaptTo(Session.class)).orElseThrow();
            Node rootNode = session.getRootNode();
            NodeIterator children = rootNode.getNodes();
            while (children.hasNext()) {
                Node child = children.nextNode();
                String childPath = child.getPath();
                childrenPaths.add(childPath);
            }
        }
        String childrenPathsString = childrenPaths.toString();
        try (Writer responseWriter = response.getWriter()) {
            responseWriter.write(childrenPathsString);
        }
    }
}
