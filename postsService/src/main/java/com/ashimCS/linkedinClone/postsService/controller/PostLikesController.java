package com.ashimCS.linkedinClone.postsService.controller;


import com.ashimCS.linkedinClone.postsService.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/likes")
@RestController
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")    // http://localhost:9010/api/v1/posts/likes/1
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        postLikeService.likePost(postId);
        return ResponseEntity.noContent().build(); // Return a successful HTTP response with status 204 No Content and no response body
    }

    @DeleteMapping("/{postId}")  // http://localhost:9010/api/v1/posts/likes/1
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) {
        postLikeService.unlikePost(postId);
        return ResponseEntity.noContent().build();

    }

}
