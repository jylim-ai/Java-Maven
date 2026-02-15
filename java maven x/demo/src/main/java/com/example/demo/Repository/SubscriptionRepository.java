package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    Optional<Subscription> findBySubscriberId(Long id);
    Optional<Subscription> findByCreatorId(Long id);
}
