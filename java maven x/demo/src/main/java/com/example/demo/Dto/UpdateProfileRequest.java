package com.example.demo.Dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    // User fields
    private String username;

    // Profile fields
    private String bio;
    private String profileImageUrl;
    private String bannerImageUrl;
    private Map<String, String> socialLinks;
}

