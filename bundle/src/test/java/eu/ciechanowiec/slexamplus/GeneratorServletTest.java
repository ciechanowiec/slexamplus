package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.test.TestEnvironment;
import lombok.SneakyThrows;
import org.apache.commons.collections4.IterableUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("PMD.CloseResource")
class GeneratorServletTest extends TestEnvironment {

    GeneratorServletTest() {
        super(ResourceResolverType.JCR_OAK);
    }

    @SneakyThrows
    @Test
    void mustGenerate() {
        GeneratorServlet generatorServlet = context.registerInjectActivateService(GeneratorServlet.class);
        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();
        generatorServlet.doGet(request, response);
        @SuppressWarnings("resource")
        ResourceResolver resourceResolver = context.resourceResolver();
        Resource resource = Optional.ofNullable(resourceResolver.getResource("/content/generated")).orElseThrow();
        Iterable<Resource> children = resource.getChildren();
        int numOfChildren = IterableUtils.size(children);
        assertEquals(1000, numOfChildren);
    }
}
