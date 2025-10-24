package eu.ciechanowiec.slexamplus.job;

import eu.ciechanowiec.sling.rocket.test.TestEnvironment;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WritersTest extends TestEnvironment {

    WritersTest() {
        super(ResourceResolverType.RESOURCERESOLVER_MOCK);
    }

    @Test
    void test() {
        Writer1 writer1 = context.registerInjectActivateService(Writer1.class);
        Writer2 writer2 = context.registerInjectActivateService(Writer2.class);
        assertAll(
            () -> assertEquals(JobConsumer.JobResult.OK, writer1.process(mock(Job.class))),
            () -> assertEquals(JobConsumer.JobResult.OK, writer2.process(mock(Job.class)))
        );
    }
}
