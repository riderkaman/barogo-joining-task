package com.test.barogo.delivery.dto;

import com.test.barogo.delivery.domain.Delivery;
import com.test.barogo.delivery.service.ValidDateRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class DeliveryDto {

    @Data
    @ValidDateRange
    public static class MemberDeliveryReq {

        @NotNull(message = "startDate is required.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;

        @NotNull(message = "endDate is required.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;
    }

    @Data
    public static class MemberDeliveryRes {
        private String deliveryId; // 배달의 고유 ID
        private String orderId; // 주문의 고유 ID
        private String destinationAddress;
        private LocalDate orderDate;
        private String memberId;
        private String status;

        public MemberDeliveryRes(Delivery delivery) {
            this.deliveryId = delivery.getDeliveryId();
            this.orderId = delivery.getOrderId();
            this.destinationAddress = delivery.getDestinationAddress();
            this.orderDate = delivery.getOrderDate();
            this.memberId = delivery.getMemberId();
            this.status = delivery.getStatus();
        }
    }

    @Data
    public static class DestinationAddressChangeReq {
        @NotBlank
        private String deliveryId;

        @NotBlank
        private String destinationAddress;
    }

    @Data
    public static class DestinationAddressChangeRes {
        private String deliveryId;
        private String destinationAddress;
        private String status;

        public DestinationAddressChangeRes(String deliveryId, String destinationAddress, String status) {
            this.deliveryId = deliveryId;
            this.destinationAddress = destinationAddress;
            this.status = status;
        }
    }
}
