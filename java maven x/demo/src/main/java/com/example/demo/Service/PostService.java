package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.Component.PostMapper;
import com.example.demo.Component.UserMapper;
import com.example.demo.Dto.PostRequest;
import com.example.demo.Dto.PostResponse;
import com.example.demo.Model.Media;
import com.example.demo.Model.Post;
import com.example.demo.Model.User;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;




    public boolean isOwner(Long postId, UUID userId) {
        User creator = userRepository.findByUuid(userId)
            .orElseThrow(() -> new RuntimeException("Creator not found"));

        return postRepository.existsByIdAndCreatorId(postId, creator.getId());
    }

    public PostResponse getPostByUuid(UUID id) {
        


        
        
            
        
        User creator = userRepository.findByUuid(id)
            .orElseThrow(() -> new RuntimeException("Creator not found"));
        
        Post post = postRepository.findByCreatorId(creator.getId())
            .orElseThrow(() -> new RuntimeException("Post not found"));

        return postMapper.toDto(post);
    }

    public PostResponse createPost(PostRequest req, Authentication auth) {

        User creator = userRepository.findByUuid(UUID.fromString(auth.getName()))
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        Post post = new Post();
        post.setCreator(creator);
        post.setTitle(req.getTitle());
        post.setDescription(req.getDescription());
        post.setIsPaid(req.getIsPaid() != null ? req.getIsPaid() : true);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        if (req.getMediaList() != null) {
            List<Media> mediaEntities = req.getMediaList().stream().map(m -> {
                Media media = new Media();
                media.setUrl(m.getUrl());
                media.setFilename(m.getFilename());
                media.setType(m.getType());
                media.setSize(m.getSize());
                media.setPost(post);
                return media;
            }).toList();

            post.setMediaList(mediaEntities);
        }

        postRepository.save(post);
        
        return postMapper.toDto(post);
    }

    public PostResponse updatePost(Long id, PostRequest req, Authentication auth) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        
        User creator = userRepository.findByUuid(UUID.fromString(auth.getName()))
                .orElseThrow(() -> new RuntimeException("Creator not found"));


        if (post.getCreator() != creator ) throw new RuntimeException("Access denied");

        if (req.getTitle() != null) post.setTitle(req.getTitle());
        if (req.getDescription() != null) post.setDescription(req.getDescription());
        if (req.getIsPaid() != null) post.setIsPaid(req.getIsPaid());
        post.setUpdatedAt(LocalDateTime.now());

        if (req.getMediaList() != null) {
            post.getMediaList().clear();

            List<Media> mediaEntities = req.getMediaList().stream().map(m -> {
                Media media = new Media();
                media.setUrl(m.getUrl());
                media.setType(m.getType());
                media.setPost(post);
                return media;
            }).toList();

            post.setMediaList(mediaEntities);
        }

        return postMapper.toDto(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        postRepository.delete(post);
    }
}

