package eu.ciechanowiec.slexamplus.models;

import org.apache.sling.api.SlingJakartaHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {Resource.class, SlingJakartaHttpServletRequest.class})
public class BooksModel {

    public String getNames() {
        return "I'm the text from the Sling Model: " + BooksModel.class.getName();
    }
}
