package com.example.demo.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Post;


public interface PostRepository extends JpaRepository<Post, Long> {
    
    Optional<Post> findByCreatorId(Long id);

    boolean existsByIdAndCreatorId(Long postId, Long creatorId);
}
