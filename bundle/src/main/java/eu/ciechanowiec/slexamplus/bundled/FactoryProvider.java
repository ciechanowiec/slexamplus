package eu.ciechanowiec.slexamplus.bundled;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(
        service = StringProvider.class,
        immediate = true,
        configurationPolicy = ConfigurationPolicy.REQUIRE
)
@Designate(
        ocd = FactoryProviderConfig.class,
        factory = true
)
@ToString
@Slf4j
@SuppressWarnings("TypeName")
public class FactoryProvider implements StringProvider {

    private String providedString;

    public FactoryProvider() {
        this.providedString = StringUtils.EMPTY;
    }

    @Activate
    @Modified
    void configure(FactoryProviderConfig config) {
        providedString = config.provided$_$string();
        log.info("Configured {}", this);
    }

    @Override
    public String provide() {
        return providedString;
    }
}
