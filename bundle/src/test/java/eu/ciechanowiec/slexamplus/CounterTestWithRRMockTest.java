package eu.ciechanowiec.slexamplus;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CounterTestWithRRMockTest extends TestEnvironment {

    private Counter counter;

    CounterTestWithRRMockTest() {
        super(ResourceResolverType.RESOURCERESOLVER_MOCK);
    }

    @BeforeEach
    void setup() {
        counter = context.registerInjectActivateService(Counter.class);
    }

    @Test
    void mustIncrementOnce() {
        int counterValue = counter.incrementByOne();
        assertEquals(NumberUtils.INTEGER_ONE, counterValue);
    }

    @Test
    void mustIncrementTwice() {
        counter.incrementByOne();
        int counterValue = counter.incrementByOne();
        assertEquals(NumberUtils.INTEGER_TWO, counterValue);
    }
}
