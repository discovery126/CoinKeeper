package org.denis.coinkeeper.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailUniqueValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUnique {
    String message() default "User with the same email already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}