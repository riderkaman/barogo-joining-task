package com.test.barogo.member.dto;

import com.test.barogo.member.service.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MemberDto {

    @Data
    @NoArgsConstructor
    public static class RegisterReq {
        @NotBlank
        private String memberInputId;

        @ValidPassword
        private String password;

        @NotBlank
        private String name;

       /* @Builder
        public Member dtoToDomain(RegisterReq req) {
            return Member.builder()
                    .memberInputId(req.memberInputId)
                    .password(req.password)
                    .email(req.email)
                    .phoneNumber(req.phoneNumber)
                    .accountType(req.accountType)
                    .created(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .updated(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
        }*/
    }

    @Data
    @NoArgsConstructor
    public static class LoginReq {
        private String memberInputId;
        private String password;
    }
}
