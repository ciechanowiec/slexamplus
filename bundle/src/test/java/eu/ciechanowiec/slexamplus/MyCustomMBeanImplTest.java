package eu.ciechanowiec.slexamplus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyCustomMBeanImplTest {

    private MyCustomMBean myCustomMBean;

    @BeforeEach
    void setup() {
        myCustomMBean = new MyCustomMBeanImpl();
    }

    @Test
    void testGetMessage() {
        assertEquals("Hello from MyCustomMBean!", myCustomMBean.getMessage());
    }

    @Test
    void testUpdateMessage() {
        String newMessage = "A new message for the test";
        myCustomMBean.updateMessage(newMessage);
        assertEquals(newMessage, myCustomMBean.getMessage());
    }

    @Test
    @SuppressWarnings("LineLength")
    void testPerformAction() {
        String input = "test-input";
        String expectedResult = "Action performed with input: 'test-input'. Current message: 'Hello from MyCustomMBean!'";
        String actualResult = myCustomMBean.performAction(input);
        assertEquals(expectedResult, actualResult);

        String newMessage = "Another message";
        myCustomMBean.updateMessage(newMessage);

        String anotherInput = "another-input";
        String expectedResultAfterUpdate = "Action performed with input: 'another-input'. Current message: 'Another message'";
        String actualResultAfterUpdate = myCustomMBean.performAction(anotherInput);
        assertEquals(expectedResultAfterUpdate, actualResultAfterUpdate);
    }
}
