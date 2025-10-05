package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.calendar.CalendarNode;
import eu.ciechanowiec.sling.rocket.calendar.YearNode;
import eu.ciechanowiec.sling.rocket.jcr.path.TargetJCRPath;
import eu.ciechanowiec.sling.rocket.test.TestEnvironment;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalendarInitializationTest extends TestEnvironment {

    CalendarInitializationTest() {
        super(ResourceResolverType.JCR_OAK);
    }

    @Test
    void calendarInitialized() {
        context.registerInjectActivateService(CalendarInitialization.class);
        List<YearNode> years = new CalendarNode(new TargetJCRPath("/content/calendar"), fullResourceAccess).years();
        assertEquals(2, years.size());
    }
}
