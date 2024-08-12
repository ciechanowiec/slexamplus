package eu.ciechanowiec.slexamplus;

import eu.ciechanowiec.sling.rocket.test.TestEnvironment;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CounterTestWithJCROakTest extends TestEnvironment {

    private Counter counter;

    CounterTestWithJCROakTest() {
        super(ResourceResolverType.JCR_OAK);
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
