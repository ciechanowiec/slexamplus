package eu.ciechanowiec.slexamplus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

@Component(
        service = BasicHTMLPage.class,
        immediate = true
)
@ServiceDescription("Provides a basic HTML page")
public class BasicHTMLPage {

    String producePage() {
        return """
               <!DOCTYPE html>
               <html>
               <head>
                   <title>Hello Universe Page</title>
               </head>
               <body>
                   <h1>Hello, Universe!</h1>
               </body>
               </html>
               """;
    }
}
