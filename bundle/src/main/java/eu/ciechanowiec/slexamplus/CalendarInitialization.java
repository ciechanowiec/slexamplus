package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.calendar.StagedCalendarNode;
import eu.ciechanowiec.sling.rocket.commons.FullResourceAccess;
import eu.ciechanowiec.sling.rocket.jcr.NodeProperties;
import eu.ciechanowiec.sling.rocket.jcr.path.TargetJCRPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import java.time.Year;
import java.util.Optional;

@Component(
    service = CalendarInitialization.class,
    immediate = true
)
@Slf4j
public class CalendarInitialization {

    @SuppressWarnings({"MagicNumber", "squid:S1075"})
    @Activate
    public CalendarInitialization(
        @Reference(cardinality = ReferenceCardinality.MANDATORY)
        FullResourceAccess fullResourceAccess
    ) {
        try (ResourceResolver resourceResolver = fullResourceAccess.acquireAccess()) {
            String calendarPathRaw = "/content/calendar";
            Optional.ofNullable(resourceResolver.getResource(calendarPathRaw))
                .ifPresentOrElse(
                    resource -> log.info("Calendar already exists ({}), skipping initialization", resource),
                    () -> {
                        TargetJCRPath calendarPath = new TargetJCRPath(calendarPathRaw);
                        new StagedCalendarNode(Year.of(2024), Year.of(2025), fullResourceAccess).save(calendarPath);
                        NodeProperties nodeProperties = new NodeProperties(calendarPath, fullResourceAccess);
                        nodeProperties.setProperty(
                            ResourceResolver.PROPERTY_RESOURCE_TYPE, "slexamplus/application/calendar"
                        );
                    }
                );
        }
    }
}
