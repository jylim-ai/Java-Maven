package com.example.demo.Controller;

import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/stripe")
public class StripeWebhookController {

    private static final String ENDPOINT_SECRET = System.getenv("STRIPE_WEBHOOK_SECRET"); // Set in env



    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) throws IOException {
        // Read raw bytes directly
        byte[] payloadBytes = request.getInputStream().readAllBytes();
        String payload = new String(payloadBytes, StandardCharsets.UTF_8);
        String sigHeader = request.getHeader("Stripe-Signature");
        

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, ENDPOINT_SECRET);

            switch (event.getType()) {
                case "checkout.session.completed":
                    Session session = (Session) event.getDataObjectDeserializer()
                            .getObject()
                            .orElse(null);
                    if (session != null) {
                        // ✅ Payment succeeded → update database
                        System.out.println("💰 Payment succeeded: " + session.getId());
                        // TODO: mark order or plan as paid
                    }
                    break;

                default:
                    System.out.println("Unhandled event type: " + event.getType());
            }

            return ResponseEntity.ok("");
            
        } catch (Exception e) {
            System.out.println("⚠️ Webhook signature verification failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    // Utility to read request body
    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        return sb.toString();
    }
}

