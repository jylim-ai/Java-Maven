package com.example.demo.Dto;

import lombok.Data;

@Data
public class MediaResponse {
    private String url;
    private String filename;
    private String type;
    private Long size;
}

