package eu.ciechanowiec.slexamplus;

import lombok.Getter;
import lombok.ToString;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.REQUIRED
)
@ToString
class UsualModel {

    private final String primaryType;
    @Getter
    private final String customProperty;
    private final String nonExistentProperty;

    @Inject
    UsualModel(
            @ValueMapValue(name = JcrConstants.JCR_PRIMARYTYPE)
            @Default(values = JcrConstants.NT_UNSTRUCTURED)
            String primaryType,
            @ValueMapValue(name = "customProperty")
            @Default(values = "Default value for a custom property")
            String customProperty,
            @ValueMapValue(name = "nonExistentProperty")
            @Default(values = "Default value for a non-existent property")
            String nonExistentProperty
    ) {
        this.primaryType = primaryType;
        this.customProperty = customProperty;
        this.nonExistentProperty = nonExistentProperty;
    }
}
