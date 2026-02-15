package com.example.demo.Dto;

import java.util.Set;
import java.util.UUID;

import com.example.demo.Model.User.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Set<Role> role;
}
