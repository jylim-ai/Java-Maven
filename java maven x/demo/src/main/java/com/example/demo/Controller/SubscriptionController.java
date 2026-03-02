package com.example.demo.Controller;

import java.util.UUID;
import java.util.concurrent.Flow.Subscription;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.SubscriptionRequest;
import com.example.demo.Model.User;
import com.example.demo.Service.SubscriptionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/api/subscription")
@RequiredArgsConstructor
@RestController
public class SubscriptionController {
    
    private final SubscriptionService subscriptionService;





    @PreAuthorize("#id.toString() == authentication.name")
    @GetMapping("{id}/creator")
    public ResponseEntity<?> getCreator(@PathVariable UUID id) {
        return ResponseEntity.ok(subscriptionService.getCreatorByUuid(id));
    }




    @PreAuthorize("#id.toString() == authentication.name")
    @GetMapping("{id}/subscriber")
    public ResponseEntity<?> getSubscriber(@PathVariable UUID id) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionByUuid(id));
    }
    
    @PreAuthorize("#req.subscriberId.toString() == authentication.name")
    @PostMapping("/create")
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionRequest req, Authentication auth) {
        return ResponseEntity.ok(subscriptionService.createSubscription(req, auth));
    }

    @PreAuthorize("#req.subscriberId.toString() == authentication.name")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionRequest req, Authentication auth) {

        


        return ResponseEntity.ok(subscriptionService.updateSubscription(id, req, auth));
    }
}
