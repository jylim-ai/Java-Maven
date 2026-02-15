package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    private Double amount;
    private String currency = "USD";
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private LocalDateTime paymentDate = LocalDateTime.now();

    public enum Status {
        PENDING, SUCCESS, FAILED
    }
}

