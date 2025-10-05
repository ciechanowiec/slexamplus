package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.calendar.StagedCalendarNode;
import eu.ciechanowiec.sling.rocket.commons.FullResourceAccess;
import eu.ciechanowiec.sling.rocket.jcr.NodeProperties;
import eu.ciechanowiec.sling.rocket.jcr.path.TargetJCRPath;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import java.time.Year;

@Component(
    service = CalendarInitialization.class,
    immediate = true
)
public class CalendarInitialization {

    @SuppressWarnings("MagicNumber")
    @Activate
    public CalendarInitialization(
        @Reference(cardinality = ReferenceCardinality.MANDATORY)
        FullResourceAccess fullResourceAccess
    ) {
        TargetJCRPath calendarPath = new TargetJCRPath("/content/calendar");
        new StagedCalendarNode(Year.of(2024), Year.of(2025), fullResourceAccess).save(calendarPath);
        NodeProperties nodeProperties = new NodeProperties(calendarPath, fullResourceAccess);
        nodeProperties.setProperty(ResourceResolver.PROPERTY_RESOURCE_TYPE, "slexamplus/application/calendar");
    }
}
