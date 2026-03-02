package com.example.demo.Dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.example.demo.Model.Plan;
import com.example.demo.Model.Subscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponse {
    private Long id;

    private UUID subscriberId;
    private String subscriberName;
    private PlanResponse plan;
    private Instant startDate;
    private Instant endDate;
    private Subscription.Status status; // optional
}
