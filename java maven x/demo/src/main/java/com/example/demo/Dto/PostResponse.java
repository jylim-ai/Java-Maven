package com.example.demo.Dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.Model.Media;
import com.example.demo.Model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {
    private Long id;        // required for create
    private Long creator;
    private String title;
    private String description;
    private Boolean isPaid;        // optional, default true
    
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MediaResponse> mediaList; // optional
}

