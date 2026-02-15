package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Dto.SubscriptionRequest;
import com.example.demo.Dto.UpdateProfileRequest;
import com.example.demo.Model.Profile;
import com.example.demo.Model.Subscription;
import com.example.demo.Model.User;
import com.example.demo.Repository.ProfileRepository;
import com.example.demo.Repository.SubscriptionRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/user")
public class UserController {


    
    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private SubscriptionRepository subscriptionRepository;
    private UserService userService;

    public UserController(UserRepository userRepository, ProfileRepository profileRepository, SubscriptionRepository subscriptionRepository, UserService userService) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
    }
    
    @PreAuthorize("#id.toString() == authentication.name")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser (@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserByUuid(id));
    
    }


    @PreAuthorize("#id.toString() == authentication.name")
    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getProfile (@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getProfileByUserId(id));
    }

    @PreAuthorize("#id.toString() == authentication.name")
    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable UUID id, @Valid @RequestBody UpdateProfileRequest req) {
        return ResponseEntity.ok(userService.updateProfile(id, req));
    }

    



    
}
