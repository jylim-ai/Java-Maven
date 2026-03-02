package com.example.demo.Dto;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.example.demo.Model.User.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private Set<Role> role;
    private List<PlanResponse> planList;
    private List<SubscriptionResponse> subscriptionList;
}
