package com.test.barogo.delivery.service;

import com.test.barogo.delivery.domain.Delivery;
import com.test.barogo.delivery.domain.DeliveryStatus;
import com.test.barogo.delivery.dto.DeliveryDto;
import com.test.barogo.delivery.repository.DeliveryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    @Test
    @DisplayName("대상 배달 ID가 없는 경우 에러")
    void 대상_배달_ID가_없는_경우_에러() {
        // Given
        String deliveryId = "noId";
        DeliveryDto.DestinationAddressChangeReq req = new DeliveryDto.DestinationAddressChangeReq();
        req.setDeliveryId(deliveryId);
        req.setDestinationAddress("없는주소");

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> deliveryService.updateDestinationAddress(req, deliveryId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Delivery Not Found");
    }

    @ParameterizedTest
    @EnumSource(value = DeliveryStatus.class, names = {"IN_PROGRESS", "COMPLETED", "CANCELLED"})
    @DisplayName("허용되지 않는 배달상태인 경우 에러")
    void 허용되지_않는_배달상태인_경우_에러(DeliveryStatus status) {
        // Given
        String deliveryId = "randomId";
        DeliveryDto.DestinationAddressChangeReq req = new DeliveryDto.DestinationAddressChangeReq();
        req.setDeliveryId(deliveryId);
        Delivery delivery = Delivery.builder()
                .status(status.name())
                .build();

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(delivery));

        // When & Then
        assertThatThrownBy(() -> deliveryService.updateDestinationAddress(req, deliveryId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Delivery Status Not Supported: " + status.name());
    }

    @Test
    @DisplayName("허용된 상태인 경우 성공")
    void 허용된_상태인_경우_성공() {
        // Given
        String deliveryId = "randomId";
        String newAddress = "새 주소";
        DeliveryDto.DestinationAddressChangeReq req = new DeliveryDto.DestinationAddressChangeReq();
        req.setDeliveryId(deliveryId);
        req.setDestinationAddress(newAddress);

        Delivery delivery = mock(Delivery.class);
        when(delivery.getStatus()).thenReturn(DeliveryStatus.PENDING.name());
        when(delivery.getDeliveryId()).thenReturn(deliveryId);

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(delivery));

        // When
        DeliveryDto.DestinationAddressChangeRes response = deliveryService.updateDestinationAddress(req, deliveryId);

        // Then
        assertThat(response.getDeliveryId()).isEqualTo(deliveryId);
        assertThat(response.getDestinationAddress()).isEqualTo(newAddress);
        assertThat(response.getStatus()).isEqualTo(DeliveryStatus.PENDING.name());

        verify(delivery).updateDeliveryDestinationAddress(newAddress);
        verify(deliveryRepository).findById(deliveryId);
    }

}
