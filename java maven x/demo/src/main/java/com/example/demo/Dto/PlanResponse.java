package com.example.demo.Dto;

import java.math.BigDecimal;

import com.example.demo.Model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanResponse {
    private Long id;
    private String name;
    private BigDecimal amount;
    private User creator;
}
