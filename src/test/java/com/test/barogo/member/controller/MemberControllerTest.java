package com.test.barogo.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.barogo.member.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private MemberController memberController; // 컨트롤러를 직접 주입

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build(); // MockMvc 설정
    }

    @Test
    @DisplayName("비밀번호가 유효할 경우 회원가입 성공")
    void registerUser_ValidPassword_Success() throws Exception {
        // Given: 유효한 비밀번호를 포함한 DTO
        MemberDto.CreateReq validMember = new MemberDto.CreateReq();
        validMember.setMemberInputId("validMemberInputId");
        validMember.setName("validMemberName");
        validMember.setPassword("ValidPassword123!@");

        // When & Then: 요청을 보내고 200 OK 응답 확인
        mockMvc.perform(post("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validMember))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호가 유효하지 않을 경우 회원가입 실패")
    void registerUser_InvalidPassword_Fail() throws Exception {
        // Given: 유효하지 않은 비밀번호 (조건 미충족)
        MemberDto.CreateReq invalidMember = new MemberDto.CreateReq();
        invalidMember.setMemberInputId("invalidMemberInputId");
        invalidMember.setName("invalidMemberName");
        invalidMember.setPassword("weakpass");

        // When & Then: 요청을 보내고 400 Bad Request 응답 확인
        mockMvc.perform(post("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidMember))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }

}