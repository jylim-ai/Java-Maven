package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.Component.ProfileMapper;
import com.example.demo.Component.UserMapper;
import com.example.demo.Dto.ProfileResponse;
import com.example.demo.Dto.UpdateProfileRequest;
import com.example.demo.Dto.UserResponse;
import com.example.demo.Model.Profile;
import com.example.demo.Model.User;
import com.example.demo.Repository.ProfileRepository;
import com.example.demo.Repository.SubscriptionRepository;
import com.example.demo.Repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;
    private SubscriptionRepository subscriptionRepository;
    
    public UserResponse getUserByUuid (UUID uuid) {
        
        if (uuid == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        User user = userRepository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDto(user);
    
    }

    public ProfileResponse getProfileByUserId(UUID uuid) {

        if (uuid == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return profileMapper.toDto(profile);
    }


    public ProfileResponse updateProfile(UUID id, UpdateProfileRequest req) {

        if (id == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        User user = userRepository.findByUuid(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // Update fields
        profile.getUser().setUsername(req.getUsername());
        profile.setBio(req.getBio());
        profile.setProfileImageUrl(req.getProfileImageUrl());
        profile.setBannerImageUrl(req.getBannerImageUrl());
        profile.setSocialLinks(req.getSocialLinks());
        profile.setUpdatedAt(LocalDateTime.now());

        return profileMapper.toDto(profile);
    }
}
