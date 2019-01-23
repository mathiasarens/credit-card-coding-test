package de.arens.publics.sapient.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = Luhn10Validator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Luhn10 {
    String message() default "Invalid checksum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
