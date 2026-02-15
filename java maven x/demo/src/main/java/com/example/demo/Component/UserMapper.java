package com.example.demo.Component;

import org.springframework.stereotype.Component;

import com.example.demo.Dto.UserResponse;
import com.example.demo.Model.User;

@Component
public class UserMapper {
    
    public UserResponse toDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRoles())
                .build();
    }
}
