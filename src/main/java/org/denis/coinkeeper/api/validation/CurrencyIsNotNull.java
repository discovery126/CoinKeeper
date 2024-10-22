package org.denis.coinkeeper.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrencyIsNotNullValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyIsNotNull {
    String message() default "Currency is not found";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}