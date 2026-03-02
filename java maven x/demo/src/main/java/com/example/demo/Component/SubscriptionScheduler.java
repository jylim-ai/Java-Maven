package com.example.demo.Component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.Model.Subscription.Status;
import com.example.demo.Repository.SubscriptionRepository;

import java.time.Instant;
import java.time.LocalDate;

@Component
public class SubscriptionScheduler {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionScheduler(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    // Runs every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void expireSubscriptions() {
        Instant today = Instant.now();
        subscriptionRepository.findAll().stream()
            .filter(sub -> sub.getEndDate() != null && !sub.getEndDate().isAfter(today))
            .forEach(sub -> {
                if (!Status.EXPIRED.equals(sub.getStatus())) {
                    sub.setStatus(Status.EXPIRED);
                    subscriptionRepository.save(sub);
                }
            });
    }
}

