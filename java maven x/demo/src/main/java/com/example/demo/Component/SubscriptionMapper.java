package com.example.demo.Component;

import org.springframework.stereotype.Component;

import com.example.demo.Dto.SubscriptionResponse;
import com.example.demo.Model.Subscription;

@Component
public class SubscriptionMapper {
    
    public SubscriptionResponse toDto(Subscription subscription) {
        return SubscriptionResponse.builder()
                .subscriberId(subscription.getSubscriber().getUuid())
                .subscriberName(subscription.getSubscriber().getUsername())
                .plan(subscription.getPlan())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .status(subscription.getStatus())

                .build();

    }
}
