package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.PostRequest;
import com.example.demo.Model.Media;
import com.example.demo.Model.Post;
import com.example.demo.Model.User;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.ProfileRepository;
import com.example.demo.Repository.SubscriptionRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/post")

public class PostController {

    private final PostService postService;

    
    public PostController(PostService postService) {
        this.postService = postService;
    }



    @PreAuthorize("#id.toString() == authentication.name")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.getPostByUuid(id));
    }
    
    @PreAuthorize("#req.creatorId.toString() == authentication.name")
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostRequest req, Authentication auth) {
        return ResponseEntity.ok(postService.createPost(req, auth));
    }

    @PreAuthorize("#req.creatorId.toString() == authentication.name")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequest req, Authentication auth) {
        return ResponseEntity.ok(postService.updatePost(id, req, auth));
    }

    @PreAuthorize("@postService.isOwner(#id, authentication.name)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
