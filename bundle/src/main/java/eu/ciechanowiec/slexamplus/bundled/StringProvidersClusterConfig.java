package eu.ciechanowiec.slexamplus.bundled;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
@SuppressWarnings("TypeName")
public @interface StringProvidersClusterConfig {

    @AttributeDefinition(
            name = "String Delimiter",
            description = "The delimiter will be used to combine strings",
            defaultValue = "###",
            type = AttributeType.STRING
    )
    String delimiter() default "###";
}
