package com.test.barogo.delivery.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity(name = "DELIVERY")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deliveryId; // 배달의 고유 ID

    private String orderId; // 주문의 고유 ID
    private String destinationAddress;
    private LocalDate orderDate;
    private String memberId;
    private String status;
}

