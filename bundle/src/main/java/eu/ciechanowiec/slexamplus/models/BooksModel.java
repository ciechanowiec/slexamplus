package eu.ciechanowiec.slexamplus.models;

import eu.ciechanowiec.slexamplus.bundled.StringProvidersCluster;
import lombok.Getter;
import org.apache.jackrabbit.vault.util.JcrConstants;
import org.apache.sling.api.SlingJakartaHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;

@SuppressWarnings("ClassCanBeRecord")
@Model(adaptables = {Resource.class, SlingJakartaHttpServletRequest.class})
@Getter
public class BooksModel {

    private final String primaryType;
    private final String customProperty;
    private final String nonExistentProperty;
    private final SlingJakartaHttpServletRequest request;
    private final Resource resource;
    private final StringProvidersCluster osgiService;

    @SuppressWarnings({"ConstructorWithTooManyParameters", "PMD.ExcessiveParameterList"})
    @Inject
    public BooksModel(
        @ValueMapValue(name = JcrConstants.JCR_PRIMARYTYPE)
        @Default(values = JcrConstants.NT_UNSTRUCTURED)
        String primaryType,
        @ValueMapValue(name = "customProperty")
        @Default(values = "Default value for a custom property")
        String customProperty,
        @ValueMapValue(name = "nonExistentProperty")
        @Default(values = "Default value for a non-existent property")
        String nonExistentProperty,
        @Self
        SlingJakartaHttpServletRequest request,
        @SlingObject
        Resource resource,
        @OSGiService
        StringProvidersCluster osgiService
    ) {
        this.primaryType = primaryType;
        this.customProperty = customProperty;
        this.nonExistentProperty = nonExistentProperty;
        this.request = request;
        this.resource = resource;
        this.osgiService = osgiService;
    }

    public String getNames() {
        return "I'm the text from the Sling Model: " + BooksModel.class.getName();
    }
}
