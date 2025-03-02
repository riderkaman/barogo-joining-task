package com.test.barogo.delivery.service;

import com.test.barogo.delivery.domain.Delivery;
import com.test.barogo.delivery.domain.DeliveryStatus;
import com.test.barogo.delivery.dto.DeliveryDto;
import com.test.barogo.delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public List<DeliveryDto.MemberDeliveryRes> getMemberDeliveries(DeliveryDto.MemberDeliveryReq req, String memberId) {
        List<Delivery> deliveries = deliveryRepository.findByMemberIdAndOrderDateBetween(memberId, req.getStartDate(), req.getEndDate());

        List<DeliveryDto.MemberDeliveryRes> res = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            DeliveryDto.MemberDeliveryRes memberDeliveryRes = new DeliveryDto.MemberDeliveryRes(delivery);
            res.add(memberDeliveryRes);
        }

        return res;
    }

    @Transactional
    public DeliveryDto.DestinationAddressChangeRes updateDestinationAddress(DeliveryDto.DestinationAddressChangeReq req, String deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery Not Found"));

        // 배달중, 배달완료, 취소의 경우 변경불가
        String status = delivery.getStatus();
        if (DeliveryStatus.IN_PROGRESS.name().equals(status) ||
        DeliveryStatus.COMPLETED.name().equals(status) ||
        DeliveryStatus.CANCELLED.name().equals(status)) {
            throw new IllegalArgumentException("Delivery Status Not Supported: " + status);
        }

        delivery.updateDeliveryDestinationAddress(req.getDestinationAddress());
        return new DeliveryDto.DestinationAddressChangeRes(delivery.getDeliveryId(), req.getDestinationAddress(),  status);
    }
}
