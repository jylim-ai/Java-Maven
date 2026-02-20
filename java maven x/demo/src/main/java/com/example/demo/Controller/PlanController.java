package com.example.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.PlanRequest;
import com.example.demo.Dto.PlanResponse;
import com.example.demo.Dto.PostRequest;
import com.example.demo.Service.PlanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    @PreAuthorize("#request.creatorId.toString() == authentication.name")
    public ResponseEntity<PlanResponse> createPlan(@RequestBody PlanRequest request) {

        return ResponseEntity.ok(planService.createPlan(request));
    }




    @PreAuthorize("#request.creatorId.toString() == authentication.name")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable Long id, @RequestBody PlanRequest request, Authentication auth) {
        return ResponseEntity.ok(planService.updatePlan(id, request, auth));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> getPlan(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlan(id));
    }
}

