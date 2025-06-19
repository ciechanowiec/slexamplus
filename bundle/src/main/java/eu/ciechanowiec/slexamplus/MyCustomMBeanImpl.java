package eu.ciechanowiec.slexamplus;

import lombok.extern.slf4j.Slf4j;
import org.apache.jackrabbit.oak.commons.jmx.AnnotatedStandardMBean;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Default implementation of {@link MyCustomMBean}.
 */
@Component(
        service = MyCustomMBean.class,
        immediate = true,
        property = "jmx.objectname=eu.ciechanowiec.slexamplus:type=Slexamplus,name=MyCustomMBean"
)
@Slf4j
public class MyCustomMBeanImpl extends AnnotatedStandardMBean implements MyCustomMBean {

    private final AtomicReference<String> message;

    @Activate
    public MyCustomMBeanImpl() {
        super(MyCustomMBean.class);
        message = new AtomicReference<>("Hello from MyCustomMBean!");
        log.info("{} activated", MyCustomMBeanImpl.class);
    }

    @Deactivate
    protected void deactivate() {
        log.info("{} deactivated", MyCustomMBeanImpl.class);
    }

    @Override
    public String getMessage() {
        return message.get();
    }

    @Override
    public void updateMessage(String message) {
        this.message.set(message);
        log.info("Message set to: {}", message);
    }

    @Override
    public String performAction(String input) {
        String result = "Action performed with input: '%s'. Current message: '%s'".formatted(input, message);
        log.info(result);
        return result;
    }
}
