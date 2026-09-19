package com.ashim.linkedinClone.postsService.service;


import com.ashim.linkedinClone.postsService.auth.AuthContextHolder;
import com.ashim.linkedinClone.postsService.entity.Post;
import com.ashim.linkedinClone.postsService.entity.PostLike;
import com.ashim.linkedinClone.postsService.event.PostLiked;
import com.ashim.linkedinClone.postsService.exception.BadRequestException;
import com.ashim.linkedinClone.postsService.exception.ResourceNotFoundException;
import com.ashim.linkedinClone.postsService.repository.PostLikeRepository;
import com.ashim.linkedinClone.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, PostLiked> postLikeKafkaTemplate;

    @Transactional
    public void likePost(Long postId) {
        // Long userId = 1L;
        //will get from context holder
        Long userId = AuthContextHolder.getCurrentUserId();  // user who liking it
        log.info("User with ID: {} Liking post with id {}", userId, postId);

        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        boolean hasAlreadyLikes = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(hasAlreadyLikes) throw new BadRequestException("Post with id " + postId + " is already liked, you cannot like the post again");

        // if the post has not been liked by user we craete a obj
        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLikeRepository.save(postLike);

        // ToDO: we can send notification to owner of post using Kafka
        PostLiked postLiked = PostLiked.builder()
                .postId(postId)
                .ownerUserId(post.getUserId())
                .likedByUserId(userId)
                .build();

        // send via kafka
        postLikeKafkaTemplate.send("post_liked_topic", postLiked);

    }

    @Transactional // Ensures checking and deleting the like happen within one database transaction.
    public void unlikePost(Long postId) { // logging differently hence new method, could do in above method
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("User with ID: {} Unliking post with id {}", userId, postId);

        postRepository.findById(postId).orElseThrow(()
                ->new ResourceNotFoundException("Post with id " + postId + " not found"));
        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId); // Checks whether this user has liked the post.
        if (!hasAlreadyLiked) throw new BadRequestException("You cannot unlike the post that you have not yet liked"); // Rejects the request if no like exists.

        postLikeRepository.deleteByUserIdAndPostId(userId, postId); // Deletes the user's like record.

    }
}
