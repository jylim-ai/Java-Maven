package com.example.demo.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.Component.SubscriptionMapper;
import com.example.demo.Dto.PostResponse;
import com.example.demo.Dto.SubscriptionRequest;
import com.example.demo.Dto.SubscriptionResponse;
import com.example.demo.Model.Plan;
import com.example.demo.Model.Subscription;
import com.example.demo.Model.User;
import com.example.demo.Repository.PlanRepository;
import com.example.demo.Repository.SubscriptionRepository;
import com.example.demo.Repository.UserRepository;
import com.vaadin.copilot.shaded.checkerframework.checker.units.qual.s;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    public List<SubscriptionResponse> getSubscriptionByUuid(UUID id) {
        



        User user = userRepository.findByUuid(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        

        List<Plan> plans = planRepository.findAllByCreator(user);

        if (plans.isEmpty()) {
            throw new RuntimeException("No plans found");
        }

        
        List<Subscription> subscription = plans.stream()
                .flatMap(p -> subscriptionRepository.findAllByPlan(p).stream())
                .collect(Collectors.toList());

        return subscriptionMapper.toDto(subscription);
    }
    
    


    public List<SubscriptionResponse> getCreatorByUuid(UUID id) {
        


        User user = userRepository.findByUuid(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Subscription> post = subscriptionRepository.findAllBySubscriberId(user.getId());

        return subscriptionMapper.toDto(post);
    }

    public List<SubscriptionResponse> createSubscription(SubscriptionRequest req, Authentication auth) {

        User subscriber = userRepository.findByUuid(UUID.fromString(auth.getName()))
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        Plan plan = planRepository.findById(req.getPlanId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        List<Subscription> subscriptions = new ArrayList<>();
        Subscription sub = new Subscription();
        sub.setSubscriber(subscriber);
        sub.setPlan(plan);
        sub.setStartDate(Instant.now());
        sub.setEndDate(Instant.now().plus(30, ChronoUnit.DAYS));
        sub.setStatus(
                req.getStatus() != null ? req.getStatus() : Subscription.Status.ACTIVE
        );
        subscriptions.add(sub);

        subscriptionRepository.saveAll(subscriptions);
        return subscriptionMapper.toDto(subscriptions);
    }

    public List<SubscriptionResponse> updateSubscription(Long id, SubscriptionRequest req, Authentication auth) {

        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        
        User subscriber = userRepository.findByUuid(UUID.fromString(auth.getName()))
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        if (subscription.getSubscriber() != subscriber) throw new RuntimeException("Access denied");

        subscription.setStartDate(Instant.now());
        subscription.setEndDate(Instant.now().plus(30, ChronoUnit.DAYS));
        if (req.getStatus() != null) subscription.setStatus(req.getStatus());

        return subscriptionMapper.toDto(Collections.singletonList(subscription));
    }
}

