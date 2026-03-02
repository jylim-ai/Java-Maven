package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Plan;
import com.example.demo.Model.User;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    
    List<Plan> findAllByCreator(User user);
}
