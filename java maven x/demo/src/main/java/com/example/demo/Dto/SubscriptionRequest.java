package com.example.demo.Dto;

import java.math.BigDecimal;

import java.util.UUID;

import com.example.demo.Model.Subscription;

import lombok.Data;

@Data
public class SubscriptionRequest {

    private UUID subscriberId;
    private Long planId;
    private Subscription.Status status; // optional, default ACTIVE
}

