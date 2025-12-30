package com.kopa.repository;

import com.kopa.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByCheckoutRequestId(String checkoutRequestId);
}
