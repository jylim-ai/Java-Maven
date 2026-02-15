package com.example.demo.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Payment;
import com.example.demo.Repository.PaymentRepository;
import com.example.demo.exception.PaymentNotFoundException;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }


    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    public List<Payment> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Transactional
    public Payment updatePaymentStatus(Long id, Payment.Status status) {
        Payment payment = getPaymentById(id);
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }


    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException(id);
        }
        paymentRepository.deleteById(id);
    }
}

