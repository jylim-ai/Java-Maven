package com.example.demo.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MediaRequest {
    private String url;
    private String filename;
    private String type; // e.g., IMAGE, VIDEO
     private Long size;

     private LocalDateTime createdAt;
}
