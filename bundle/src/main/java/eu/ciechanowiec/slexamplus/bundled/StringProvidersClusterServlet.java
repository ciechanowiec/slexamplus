package eu.ciechanowiec.slexamplus.bundled;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import javax.servlet.Servlet;
import java.io.Writer;

@Component(
        service = {StringProvidersClusterServlet.class, Servlet.class},
        immediate = true
)
@SlingServletPaths("/clustered-string")
@SuppressWarnings({"squid:S1948", "TypeName"})
@Slf4j
public class StringProvidersClusterServlet extends SlingSafeMethodsServlet {

    private final StringProvidersCluster cluster;

    @Activate
    public StringProvidersClusterServlet(
            @Reference(cardinality = ReferenceCardinality.MANDATORY)
            StringProvidersCluster cluster
    ) {
        this.cluster = cluster;
    }

    @SneakyThrows
    @Override
    protected void doGet(
            @SuppressWarnings("NullableProblems") SlingHttpServletRequest request, SlingHttpServletResponse response
    ) {
        String combinedStrings = cluster.combinedStrings();
        log.info("Will provide these combined strings: {}", combinedStrings);
        try (Writer responseWriter = response.getWriter()) {
            responseWriter.write(combinedStrings);
        }
    }
}
