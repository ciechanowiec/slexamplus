package eu.ciechanowiec.slexamplus.bundled;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

@Component(
        service = StringProvider.class,
        immediate = true
)
@ServiceRanking(Integer.MAX_VALUE)
@SuppressWarnings("TypeName")
public class BBProvider implements StringProvider {

    @Override
    public String provide() {
        return "BB";
    }
}
