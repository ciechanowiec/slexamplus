package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.commons.ResourceAccess;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@Component(
        service = {Servlet.class, GeneratorServlet.class},
        immediate = true
)
@ServiceDescription("Writes to the repository with the use of a dedicated resource resolver")
@SlingServletPaths("/generator")
@SuppressWarnings("squid:S1948")
@Slf4j
public class GeneratorServlet extends SlingSafeMethodsServlet {

    private final ResourceAccess resourceAccess;

    @Activate
    public GeneratorServlet(
            @Reference(cardinality = ReferenceCardinality.MANDATORY)
            ResourceAccess resourceAccess
    ) {
        this.resourceAccess = resourceAccess;
    }

    @Override
    @SuppressWarnings({"NullableProblems", "OverlyBroadThrowsClause"})
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        try (ResourceResolver resourceResolver = resourceAccess.acquireAccess()) {
            Resource resource = ResourceUtil.getOrCreateResource(
                    resourceResolver, "/content/generated",
                    Map.of(JcrConstants.JCR_PRIMARYTYPE, JcrResourceConstants.NT_SLING_FOLDER), null, true
            );

            IntStream.rangeClosed(1, 1000).forEach(num -> create(resource, resourceResolver));
            resourceResolver.commit();
        }

        try (Writer responseWriter = response.getWriter()) {
            responseWriter.write("DONE");
        }
    }

    @SneakyThrows
    private void create(Resource parent, ResourceResolver resourceResolver) {
        String nodeName = UUID.randomUUID().toString();
        String chatID = UUID.randomUUID().toString();
        Resource createdResource = resourceResolver.create(parent, nodeName, Map.of(
                JcrConstants.JCR_PRIMARYTYPE, JcrResourceConstants.NT_SLING_FOLDER,
                "chatID", chatID
        ));
        log.info("Created resource: {}", createdResource);
    }
}
