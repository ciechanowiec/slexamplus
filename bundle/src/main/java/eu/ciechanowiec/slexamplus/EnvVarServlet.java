package eu.ciechanowiec.slexamplus;

import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.metatype.annotations.Designate;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.Writer;

@Component(
        service = {Servlet.class, EnvVarServlet.class},
        immediate = true,
        configurationPolicy = ConfigurationPolicy.REQUIRE
)
@ServiceDescription("Shows an environmental variable supplied in the configuration")
@SlingServletPaths("/env-var")
@SuppressWarnings("squid:S1948")
@Slf4j
@Designate(
        ocd = EnvVarServletConfig.class
)
public class EnvVarServlet extends SlingSafeMethodsServlet {

    private String variable;

    @Activate
    public EnvVarServlet(EnvVarServletConfig config) {
        variable = config.env$_$variable();
    }

    @Activate
    @Modified
    void configure(EnvVarServletConfig config) {
        variable = config.env$_$variable();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String message = String.format("Environment variable is set to '%s'", variable);
        try (Writer responseWriter = response.getWriter()) {
            responseWriter.write(message);
        }
    }
}
