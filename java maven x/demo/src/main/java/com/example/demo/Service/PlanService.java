package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.Component.PlanMapper;
import com.example.demo.Dto.PlanRequest;
import com.example.demo.Dto.PlanResponse;
import com.example.demo.Dto.PostRequest;
import com.example.demo.Dto.PostResponse;
import com.example.demo.Model.Plan;
import com.example.demo.Model.User;
import com.example.demo.Repository.PlanRepository;
import com.example.demo.Repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final PlanMapper planMapper;

    public PlanResponse createPlan(PlanRequest request) {

        User creator = userRepository.findByUuid(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        Plan plan = new Plan();
        plan.setName(request.getName());
        plan.setAmount(request.getAmount());
        plan.setCreator(creator);

        planRepository.save(plan);

        return planMapper.toDto(plan);
    }


    public PlanResponse updatePlan(Long id, PlanRequest req, Authentication auth) {

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        
        User creator = userRepository.findByUuid(UUID.fromString(auth.getName()))
                .orElseThrow(() -> new RuntimeException("Creator not found"));


        if (plan.getCreator() != creator ) throw new RuntimeException("Access denied");

        if (req.getName() != null) plan.setName(req.getName());
        if (req.getAmount() != null) plan.setAmount(req.getAmount());

        return planMapper.toDto(plan);
    }

    @Transactional
    public PlanResponse getPlan(Long id) {

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));


        return planMapper.toDto(plan);
    }
}

