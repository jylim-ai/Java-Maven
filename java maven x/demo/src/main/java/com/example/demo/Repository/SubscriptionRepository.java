package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Plan;
import com.example.demo.Model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    List<Subscription> findAllBySubscriberId(Long id);

    List<Subscription> findAllByPlan(Plan plan);
}
