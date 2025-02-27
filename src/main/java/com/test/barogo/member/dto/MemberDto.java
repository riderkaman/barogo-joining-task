package com.test.barogo.member.dto;

import com.test.barogo.member.domain.Member;
import com.test.barogo.member.service.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MemberDto {

    @Data
    @NoArgsConstructor
    public static class CreateReq {
        @NotBlank
        private String memberInputId;

        @ValidPassword
        private String password;

        @NotBlank
        private String name;

        @Builder
        public Member dtoToDomain(MemberDto.CreateReq req) {
            return Member.builder()
                    .memberInputId(req.getMemberInputId())
                    .encodedPassword(req.getPassword())
                    .name(req.getName())
                    .created(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .updated(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    public static class CreateRes {

        private String memberId;
        private String memberInputId;
        private String name;

        public CreateRes(Member member) {
            this.memberId = member.getMemberId();
            this.memberInputId = member.getMemberInputId();
            this.name = member.getName();
        }
    }

    @Data
    @NoArgsConstructor
    public static class LoginReq {
        private String memberInputId;
        private String password;
    }
}
