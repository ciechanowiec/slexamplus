package eu.ciechanowiec.slexamplus.bundled;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
@SuppressWarnings("TypeName")
public @interface FactoryProviderConfig {

    @AttributeDefinition(
            name = "Provided String",
            description = "String that is provided by this factory",
            defaultValue = "FACTOROIUS_STRINGUS",
            type = AttributeType.STRING
    )
    String provided$_$string() default "FACTOROIUS_STRINGUS";
}
