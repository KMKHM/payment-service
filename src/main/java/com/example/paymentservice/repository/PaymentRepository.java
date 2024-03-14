package com.example.paymentservice.repository;

import com.example.paymentservice.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Pay, Long> {
}
