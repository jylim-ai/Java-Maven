package com.example.demo.Dto;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    
    private Long id;
    private String bio;
    private String profileImageUrl;
    private String bannerImageUrl;
    private Map<String, String> socialLinks;
}
