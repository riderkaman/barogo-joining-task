package com.test.barogo.delivery.service;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    String message() default "startDate must be before endDate, and the maximum query period is 3 days.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
