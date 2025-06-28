package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.test.TestEnvironment;
import lombok.SneakyThrows;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomNodeTypeTest extends TestEnvironment {

    CustomNodeTypeTest() {
        super(ResourceResolverType.JCR_OAK);
    }

    @SuppressWarnings("resource")
    @SneakyThrows
    @Test
    void test() {
        registerNodeTypes(loadResourceIntoFile("SLING-INF/notetypes/nodetypes.cnd"));
        context.build().resource(
            "/content/custom-node", Map.of(JcrConstants.JCR_PRIMARYTYPE, "slexamplus:CustomNodeType")
        ).commit();
        String actualNT = Optional.ofNullable(
                context.resourceResolver().getResource("/content/custom-node")
            ).map(resource -> resource.adaptTo(ValueMap.class))
            .map(valueMap -> valueMap.get(JcrConstants.JCR_PRIMARYTYPE, String.class))
            .orElseThrow();
        assertEquals("slexamplus:CustomNodeType", actualNT);
    }
}
