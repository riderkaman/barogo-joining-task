package com.test.barogo.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.barogo.delivery.dto.DeliveryDto;
import com.test.barogo.delivery.service.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeliveryControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private DeliveryController deliveryController; // 컨트롤러를 직접 주입

    @Mock
    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
        mockMvc = MockMvcBuilders.standaloneSetup(deliveryController).build(); // MockMvc 설정
    }

    @Test
    @DisplayName("주어진 날짜가 유효한 경우 성공")
    void 주어진_날짜가_유효한_경우_성공() throws Exception {
        // Given:  서비스에서 빈 리스트 리턴
        DeliveryDto.MemberDeliveryReq req = new DeliveryDto.MemberDeliveryReq();
        String memberId = "testMemId";
        doReturn(new ArrayList<DeliveryDto.MemberDeliveryRes>()).when(deliveryService).getMemberDeliveries(req, memberId);

        // When & Then: 요청을 보내고 200 OK 응답 확인
        mockMvc.perform(get("/api/v1/delivery/testMemId")
                .param("startDate", LocalDate.now().toString())
                .param("endDate", LocalDate.now().plusDays(3).toString())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("날짜가 하나 빠진 경우 실패")
    void 날짜가_하나_빠진_경우_실패() throws Exception {
        // Given:  서비스에서 빈 리스트 리턴
        DeliveryDto.MemberDeliveryReq req = new DeliveryDto.MemberDeliveryReq();
        String memberId = "testMemId";
        doReturn(new ArrayList<DeliveryDto.MemberDeliveryRes>()).when(deliveryService).getMemberDeliveries(req, memberId);

        // When & Then: 요청을 보내고 400 Bad Request 응답 확인
        mockMvc.perform(get("/api/v1/delivery/testMemId")
                        .param("startDate", LocalDate.now().toString())
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("날짜가 3일을 넘어간 경우 실패")
    void 날짜가_3일을_넘어간_경우_실패() throws Exception {
        // Given:  서비스에서 빈 리스트 리턴
        DeliveryDto.MemberDeliveryReq req = new DeliveryDto.MemberDeliveryReq();
        String memberId = "testMemId";
        doReturn(new ArrayList<DeliveryDto.MemberDeliveryRes>()).when(deliveryService).getMemberDeliveries(req, memberId);

        // When & Then: 요청을 보내고 400 Bad Request 응답 확인
        mockMvc.perform(get("/api/v1/delivery/testMemId")
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().plusDays(4).toString())
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }

}