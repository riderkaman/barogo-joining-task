package com.test.barogo.delivery.service;

import com.test.barogo.delivery.dto.DeliveryDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.temporal.ChronoUnit;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, DeliveryDto.MemberDeliveryReq> {

    @Override
    public boolean isValid(DeliveryDto.MemberDeliveryReq req, ConstraintValidatorContext context) {
        if (req.getStartDate() == null || req.getEndDate() == null) {
            return false;
        }

        if (req.getStartDate().isAfter(req.getEndDate())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("startDate must be before endDate.")
                    .addConstraintViolation();
            return false;
        }

        long daysBetween = ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate());
        if (daysBetween > 3) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The maximum query period is 3 days.")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
