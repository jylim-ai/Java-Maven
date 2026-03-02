package com.example.demo.Dto;

import java.math.BigDecimal;

import com.example.demo.Model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponse {
    private Long id;
    private String name;
    private BigDecimal amount;
    private UserResponse creator;
}
