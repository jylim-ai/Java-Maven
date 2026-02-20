package com.example.demo.Controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Configuration.StripeProperties;
import com.example.demo.Model.Payment;
import com.example.demo.Service.PaymentService;
import com.example.demo.Service.PlanService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PlanService planService;
    private final StripeProperties stripeProperties;

    
    


    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeProperties.getSecretKey();
    }
    
    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> createCheckoutSession(@RequestBody Long id) throws StripeException {
        BigDecimal plan = planService.getPlan(id).getAmount();
        // PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setAmount(plan.multiply(BigDecimal.valueOf(100)).longValue()).setCurrency("usd").build();


        // PaymentIntent paymentIntent = PaymentIntent.create(params);


        // System.out.println(paymentIntent.getClientSecret());

        // return ResponseEntity.ok(paymentIntent.getClientSecret());

        SessionCreateParams params =
            SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("https://localhost:8080/api/payments/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("https://localhost:8080/api/payments/cancel")
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(plan.longValue()) // $50.00
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Premium Access")
                                        .build()
                                    )
                                    .build()
                        )
                        .build()
                )
                .build();

            Session session = Session.create(params);

            return ResponseEntity.ok(Map.of("url", session.getUrl()));
    }

    @GetMapping("/success/session_id={param}")
    public ResponseEntity<?> getPaymentSuccess(@RequestParam String param) {
        return ResponseEntity.ok(param);
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> getPaymentCancel(@RequestParam String param) {
        return ResponseEntity.ok(param);
    }
    

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUser(userId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Payment> updateStatus(
            @PathVariable Long id,
            @RequestParam Payment.Status status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}

