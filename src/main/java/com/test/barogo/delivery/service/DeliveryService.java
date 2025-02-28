package com.test.barogo.delivery.service;

import com.test.barogo.delivery.domain.Delivery;
import com.test.barogo.delivery.dto.DeliveryDto;
import com.test.barogo.delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
}
