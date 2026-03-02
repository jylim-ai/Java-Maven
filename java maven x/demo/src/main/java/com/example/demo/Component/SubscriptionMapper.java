package com.example.demo.Component;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.Dto.PlanResponse;
import com.example.demo.Dto.PlanResponse;
import com.example.demo.Dto.SubscriptionResponse;
import com.example.demo.Dto.UserResponse;
import com.example.demo.Model.Subscription;

@Component
public class SubscriptionMapper {
    
    public List<SubscriptionResponse> toDto(List<Subscription> subscription) {
        return subscription.stream()
        .map(sub -> SubscriptionResponse.builder()
                .id(sub.getId())
                .subscriberId(sub.getSubscriber().getUuid())
                .subscriberName(sub.getSubscriber().getUsername())
                .plan(
                    PlanResponse.builder()
                        .id(sub.getPlan().getId())
                        .name(sub.getPlan().getName())
                        .amount(sub.getPlan().getAmount())
                        .creator(
                            UserResponse.builder()
                            .id(sub.getPlan().getCreator().getUuid())
                            .username(sub.getPlan().getCreator().getUsername())
                            .email(sub.getPlan().getCreator().getEmail())
                            .build()
                        )
                        .build()
                )
                .startDate(sub.getStartDate())
                .endDate(sub.getEndDate())
                .status(sub.getStatus())

                .build())
            .collect(Collectors.toList());
        

    }
}
