package com.example.demo.Component;

import java.util.concurrent.Flow.Subscription;

import org.springframework.stereotype.Component;

import com.example.demo.Dto.MediaResponse;
import com.example.demo.Dto.PlanResponse;
import com.example.demo.Dto.SubscriptionResponse;
import com.example.demo.Dto.UserResponse;
import com.example.demo.Model.User;

@Component
public class UserMapper {
    
    public UserResponse toDto(User user) {
        return UserResponse.builder()
                .id(user.getUuid())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRoles())
                .planList(
                    user.getPlanList().stream()
                    .map(m -> {
                        PlanResponse plan = new PlanResponse();
                        plan.setId(m.getId());
                        plan.setName(m.getName());
                        plan.setAmount(m.getAmount());
                        plan.setCreator(
                            UserResponse.builder()
                            .id(m.getCreator().getUuid())
                            .username(m.getCreator().getUsername())
                            .email(m.getCreator().getEmail())
                            .build()
                        );


                        return plan;
                    })
            
            
                    .toList()
                )



                .subscriptionList(
                    user.getSubscriptionList().stream()
                    .map(m -> {
                        SubscriptionResponse subscription = new SubscriptionResponse();
                        subscription.setId(m.getId());
                        subscription.setSubscriberId(m.getSubscriber().getUuid());
                        subscription.setSubscriberName(m.getSubscriber().getUsername());
                        subscription.setPlan(
                            PlanResponse.builder()
                            .id(m.getPlan().getId())
                            .name(m.getPlan().getName())
                            .amount(m.getPlan().getAmount())
                            .creator(
                                UserResponse.builder()
                                .id(m.getPlan().getCreator().getUuid())
                                .username(m.getPlan().getCreator().getUsername())
                                .email(m.getPlan().getCreator().getEmail())
                                .build()
                            )
                        .build()
                        );


                        return subscription;
                    })
            
            
                    .toList()
                )
                .build();
    }
}
