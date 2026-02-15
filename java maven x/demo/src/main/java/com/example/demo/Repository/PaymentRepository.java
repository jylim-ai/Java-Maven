package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Payment;

public interface PaymentRepository extends JpaRepository <Payment, Long> {
    List<Payment> findByUserId(Long userId);

    List<Payment> findByStatus(Payment.Status status);
}
