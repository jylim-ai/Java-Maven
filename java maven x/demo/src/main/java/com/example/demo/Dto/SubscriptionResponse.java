package com.example.demo.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.example.demo.Model.Plan;
import com.example.demo.Model.Subscription;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionResponse {
    private Long id;

    private UUID subscriberId;
    private String subscriberName;
    private Plan plan;
    private LocalDate startDate;
    private LocalDate endDate;
    private Subscription.Status status; // optional
}
