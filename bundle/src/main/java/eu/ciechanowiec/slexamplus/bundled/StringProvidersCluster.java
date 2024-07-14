package eu.ciechanowiec.slexamplus.bundled;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component(
        service = StringProvidersCluster.class,
        immediate = true,
        configurationPolicy = ConfigurationPolicy.REQUIRE
)
@Designate(
        ocd = StringProvidersClusterConfig.class
)
@ToString
@Slf4j
public class StringProvidersCluster {

    @Reference(
            cardinality = ReferenceCardinality.AT_LEAST_ONE,
            policy = ReferencePolicy.DYNAMIC,
            policyOption = ReferencePolicyOption.GREEDY
    )
    @ToString.Exclude
    private final Collection<StringProvider> stringProviders;
    private String delimiter;

    @Activate
    public StringProvidersCluster(StringProvidersClusterConfig config) {
        delimiter = config.delimiter();
        stringProviders = new ArrayList<>();
        log.info("Initialized {}", this);
    }

    @Modified
    void configure(StringProvidersClusterConfig config) {
        delimiter = config.delimiter();
        log.info("Configured {}", this);
    }

    String combinedStrings() {
        return stringProviders.stream()
                              .map(StringProvider::provide)
                              .sorted()
                              .collect(Collectors.joining(delimiter));
    }
}
