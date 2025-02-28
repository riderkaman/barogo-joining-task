package com.test.barogo.delivery.repository;

import com.test.barogo.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {

    List<Delivery> findByMemberIdAndOrderDateBetween(String memberId, LocalDate startDate, LocalDate endDate);
}
