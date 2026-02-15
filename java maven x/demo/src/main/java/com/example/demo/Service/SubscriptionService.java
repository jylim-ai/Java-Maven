package com.example.demo.Service;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.Component.SubscriptionMapper;
import com.example.demo.Dto.PostResponse;
import com.example.demo.Dto.SubscriptionRequest;
import com.example.demo.Dto.SubscriptionResponse;
import com.example.demo.Model.Subscription;
import com.example.demo.Model.User;
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
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionResponse getSubscriptionByUuid(UUID id) {
        



        User user = userRepository.findByUuid(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Subscription subscription = subscriptionRepository.findByCreatorId(user.getId())
            .orElseThrow(() -> new RuntimeException("Subscription not found"));

        return subscriptionMapper.toDto(subscription);
    }
    
    


    public SubscriptionResponse getCreatorByUuid(UUID id) {
        


        User user = userRepository.findByUuid(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Subscription post = subscriptionRepository.findBySubscriberId(user.getId())
            .orElseThrow(() -> new RuntimeException("Creator not found"));

        return subscriptionMapper.toDto(post);
    }

    public SubscriptionResponse createSubscription(SubscriptionRequest req, Authentication auth) {

        User subscriber = userRepository.findByUuid(UUID.fromString(auth.getName()))
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        User creator = userRepository.findByUuid(req.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setCreator(creator);
        subscription.setPrice(req.getPrice());
        subscription.setStartDate(req.getStartDate());
        subscription.setEndDate(req.getEndDate());
        subscription.setStatus(
                req.getStatus() != null ? req.getStatus() : Subscription.Status.ACTIVE
        );

        subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(subscription);
    }

    public SubscriptionResponse updateSubscription(Long id, SubscriptionRequest req, Authentication auth) {

        Subscription subscription = subscriptionRepository.findById(req.getId())
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        
        User subscriber = userRepository.findByUuid(UUID.fromString(auth.getName()))
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        if (subscription.getSubscriber() != subscriber) throw new RuntimeException("Access denied");

        if (req.getPrice() != null) subscription.setPrice(req.getPrice());
        if (req.getStartDate() != null) subscription.setStartDate(req.getStartDate());
        if (req.getEndDate() != null) subscription.setEndDate(req.getEndDate());
        if (req.getStatus() != null) subscription.setStatus(req.getStatus());

        return subscriptionMapper.toDto(subscription);
    }
}

