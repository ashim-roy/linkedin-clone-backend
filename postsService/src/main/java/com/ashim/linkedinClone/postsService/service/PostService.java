package com.ashim.linkedinClone.postsService.service;


import com.ashim.linkedinClone.postsService.auth.AuthContextHolder;
import com.ashim.linkedinClone.postsService.client.ConnectionsServiceClient;
import com.ashim.linkedinClone.postsService.dto.PersonDto;
import com.ashim.linkedinClone.postsService.dto.PostCreateRequestDto;
import com.ashim.linkedinClone.postsService.dto.PostDto;
import com.ashim.linkedinClone.postsService.entity.Post;
import com.ashim.linkedinClone.postsService.event.PostCreated;
import com.ashim.linkedinClone.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsServiceClient connectionsServiceClient;
    private final KafkaTemplate<Long, PostCreated> postCreatedKafkaTemplate;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {

        log.info("Creating post for userId: {}", userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        post = postRepository.save(post);
        List<PersonDto> personDtos = connectionsServiceClient.getFirstDegreeConnections(userId);
        // send notification to each connection
        for(PersonDto person : personDtos){
            PostCreated postCreatedEvent = PostCreated.builder() // create Event
                    .postId(post.getId())
                    .content(post.getContent())
                    .ownerUserId(userId)
                    .userId(person.getUserId())
                    .build();
            postCreatedKafkaTemplate.send("post_created_topic", postCreatedEvent);
        }
        return modelMapper.map(post, PostDto.class);
    }

    // POST Service
    public PostDto getPostById(Long postId) {
        log.info("Getting post with id: {}", postId);
        Long userId = AuthContextHolder.getCurrentUserId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("Getting all posts of user with id: {}", userId); // we can use pagination here
        List<Post> postList = postRepository.findByUserId(userId);
        return postList
                .stream()  //Converts the List<Post> into a Stream for processing.
                .map(post -> modelMapper.map(post, PostDto.class))  // Converts each Post entity into a PostDto using ModelMapper.
                .collect(java.util.stream.Collectors.toList()); // Collects all PostDto objects back into a List.
    }
}
