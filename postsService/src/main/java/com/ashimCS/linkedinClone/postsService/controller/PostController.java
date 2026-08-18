package com.ashimCS.linkedinClone.postsService.controller;

import com.ashimCS.linkedinClone.postsService.dto.PostCreateRequestDto;
import com.ashimCS.linkedinClone.postsService.dto.PostDto;
import com.ashimCS.linkedinClone.postsService.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("core") //http://localhost:9010/api/v1/posts/core
public class PostController {

    private final PostService postService;
    // alternatively use constructor injection if. you dont use reqdArgCons
    //public PostController(PostService postService) {
      //  this.postService = postService;
    //}

    //create post
    @PostMapping  // POST    http://localhost:9010/api/v1/posts/core
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto,
                                              HttpServletRequest httpServletRequest) {   //httpServletRequest - from here we can get access to header
        PostDto  postDto = postService.createPost(postCreateRequestDto, 1L);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    //get a post
    @GetMapping("/{postId}")    // GET  http://localhost:9010/api/v1/posts/core/1
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    //get all posts
    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId) {
        List<PostDto> posts = postService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);
    }

    //update post

    //delete post
}
