package eu.ciechanowiec.slexamplus;

import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
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
        service = {Servlet.class, DedicatedRRServlet.class},
        immediate = true
)
@ServiceDescription("Writes to the repository with the use of a dedicated resource resolver")
@SlingServletPaths("/dedicated-rr")
@SuppressWarnings("squid:S1948")
@Slf4j
public class DedicatedRRServlet extends SlingSafeMethodsServlet {

    private final Counter counter;

    @Activate
    public DedicatedRRServlet(
            @Reference(cardinality = ReferenceCardinality.MANDATORY)
            Counter counter
    ) {
        this.counter = counter;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        int counterValue = counter.incrementByOne();
        String message = String.format("Counter: %d", counterValue);
        try (Writer responseWriter = response.getWriter()) {
            responseWriter.write(message);
        }
    }
}
