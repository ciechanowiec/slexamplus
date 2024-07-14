package eu.ciechanowiec.slexamplus;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public @interface EnvVarServletConfig {

    @AttributeDefinition(
            name = "Environmental Variable",
            description = "Value of the environmental variable",
            defaultValue = "unspecified",
            type = AttributeType.STRING
    )
    String env$_$variable() default "unspecified";
}
