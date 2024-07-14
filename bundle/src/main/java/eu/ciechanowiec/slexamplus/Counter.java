package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.commons.ResourceAccess;
import lombok.SneakyThrows;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import java.util.Map;
import java.util.Optional;

@Component(
        service = Counter.class,
        immediate = true
)
public class Counter {

    private final ResourceAccess repositoryAccess;

    @Activate
    public Counter(
            @Reference(cardinality = ReferenceCardinality.MANDATORY)
            ResourceAccess repositoryAccess
    ) {
        this.repositoryAccess = repositoryAccess;
    }

    @SneakyThrows
    int incrementByOne() {
        try (ResourceResolver resourceResolver = repositoryAccess.acquireAccess()) {
            Resource resource = ResourceUtil.getOrCreateResource(
                    resourceResolver, "/content", Map.of(), null, true
            );
            ModifiableValueMap modifiableValueMap = Optional.ofNullable(resource.adaptTo(ModifiableValueMap.class))
                                                            .orElseThrow();
            String propertyName = "counter";
            int propertyValue = modifiableValueMap.get(propertyName, NumberUtils.INTEGER_ZERO);
            int newPropertyValue = propertyValue + NumberUtils.INTEGER_ONE;
            modifiableValueMap.put(propertyName, newPropertyValue);
            resourceResolver.commit();
            return newPropertyValue;
        }
    }
}
