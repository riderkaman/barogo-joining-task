package com.test.barogo.member.service;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must be at least 12 characters long and contain at least 3 of the following: uppercase letters, lowercase letters, numbers, and special characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
