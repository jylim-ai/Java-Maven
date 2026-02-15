package com.example.demo.Component;

import org.springframework.stereotype.Component;

import com.example.demo.Dto.MediaResponse;
import com.example.demo.Dto.PostResponse;
import com.example.demo.Dto.UserResponse;
import com.example.demo.Model.Post;
import com.example.demo.Model.User;

@Component
public class PostMapper {
    
    public PostResponse toDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .creator(post.getCreator().getId())
                .title(post.getTitle())
                .description(post.getDescription())

                
                .isPaid(post.getIsPaid())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .mediaList(
                post.getMediaList().stream()
                    .map(m -> {
                        MediaResponse media = new MediaResponse();
                        media.setUrl(m.getUrl());
                        media.setFilename(m.getFilename());
                        media.setType(m.getType());
                        media.setSize(m.getSize());
                        return media;
                    })
            
            
                    .toList()
            )
                .build();
    }
}
