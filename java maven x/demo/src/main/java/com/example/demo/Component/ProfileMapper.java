package com.example.demo.Component;

import org.springframework.stereotype.Component;

import com.example.demo.Dto.ProfileResponse;
import com.example.demo.Model.Profile;

@Component
public class ProfileMapper {

    public ProfileResponse toDto(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .bio(profile.getBio())
                .profileImageUrl(profile.getProfileImageUrl())
                .bannerImageUrl(profile.getBannerImageUrl())
                .socialLinks(profile.getSocialLinks())
                .build();
    }
}
