package com.example.demo.Component;

import org.springframework.stereotype.Component;

import com.example.demo.Dto.MediaResponse;
import com.example.demo.Dto.PlanResponse;
import com.example.demo.Model.Plan;
@Component
public class PlanMapper {
    
    public PlanResponse toDto(Plan plan) {
        return PlanResponse.builder()
                .id(plan.getId())
                .name(plan.getName())
                .amount(plan.getAmount())
                .creator(plan.getCreator())


                .build();
    }
}
