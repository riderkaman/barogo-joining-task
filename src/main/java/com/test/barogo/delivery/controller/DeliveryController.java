package com.test.barogo.delivery.controller;

import com.test.barogo.delivery.dto.DeliveryDto;
import com.test.barogo.delivery.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
@Slf4j
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/{memberId}")
    public ResponseEntity<List<DeliveryDto.MemberDeliveryRes>> getMemberDeliveries(@ModelAttribute @Valid DeliveryDto.MemberDeliveryReq req,
                                                                                   @PathVariable String memberId) {
        List<DeliveryDto.MemberDeliveryRes> list = deliveryService.getMemberDeliveries(req, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PutMapping("/{deliveryId}/destination-address")
    public ResponseEntity<DeliveryDto.DestinationAddressChangeRes> updateDestinationAddress(@RequestBody DeliveryDto.DestinationAddressChangeReq req,
                                                                                            @PathVariable String deliveryId) {
        DeliveryDto.DestinationAddressChangeRes res = deliveryService.updateDestinationAddress(req, deliveryId);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


}
