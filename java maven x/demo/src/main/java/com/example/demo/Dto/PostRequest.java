package com.example.demo.Dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class PostRequest {
    private UUID creatorId;        // required for create
    private String title;
    private String description;
    private Boolean isPaid;        // optional, default true
    private List<MediaRequest> mediaList; // optional
}


