package com.example.demo.Dto;

import java.time.LocalDate;
import java.util.UUID;

import com.example.demo.Model.Subscription;

import lombok.Data;

@Data
public class SubscriptionRequest {
    private Long id;

    private UUID subscriberId;
    private UUID creatorId;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Subscription.Status status; // optional, default ACTIVE
}

