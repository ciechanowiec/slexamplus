package eu.ciechanowiec.slexamplus;

import org.apache.jackrabbit.oak.api.jmx.Description;
import org.apache.jackrabbit.oak.api.jmx.Name;

/**
 * Example of a custom MBean.
 */
@Description("Example of a custom MBean")
public interface MyCustomMBean {

    /**
     * Returns the message currently used by this service.
     *
     * @return message currently used by this service
     */
    @Description("Returns the message currently used by this service")
    String getMessage();

    /**
     * Set the message used by this service.
     *
     * @param message message that should be used by this service
     */
    @Description("Set the message used by this service")
    void updateMessage(@Name("message") @Description("Message that should be used by this service") String message);

    /**
     * Perform an action with the input.
     *
     * @param input input for the action
     * @return the result of the action
     */
    @Description("Perform an action with the input")
    String performAction(@Name("input") @Description("Input for the action") String input);
}
