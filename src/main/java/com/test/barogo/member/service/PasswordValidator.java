package com.test.barogo.member.service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final int MIN_LENGTH = 12;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.length() < MIN_LENGTH) {
            return false;
        }

        int count = 0;
        if (password.matches(".*[A-Z].*")) count++; // 대문자 포함 여부
        if (password.matches(".*[a-z].*")) count++; // 소문자 포함 여부
        if (password.matches(".*\\d.*")) count++;   // 숫자 포함 여부
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) count++; // 특수문자 포함 여부

        return count >= 3; // 4가지 중 3가지 이상 포함
    }
}
