package com.example.demo.Dto;

import java.time.LocalDate;
import java.util.UUID;

import com.example.demo.Model.Subscription;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionResponse {
    private Long id;

    private UUID subscriberId;
    private String subscriberName;
    private UUID creatorId;
    private String creatorName;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Subscription.Status status; // optional
}
