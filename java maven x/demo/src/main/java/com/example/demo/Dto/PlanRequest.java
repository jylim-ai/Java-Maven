package com.example.demo.Dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class PlanRequest {
    private String name;
    private BigDecimal amount;
    private UUID creatorId;
}
