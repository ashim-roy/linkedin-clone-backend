package com.ashimCS.linkedinClone.postsService.service;


import com.ashimCS.linkedinClone.postsService.entity.Post;
import com.ashimCS.linkedinClone.postsService.entity.PostLike;
import com.ashimCS.linkedinClone.postsService.exception.BadRequestException;
import com.ashimCS.linkedinClone.postsService.exception.ResourceNotFoundException;
import com.ashimCS.linkedinClone.postsService.repository.PostLikeRepository;
import com.ashimCS.linkedinClone.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void likePost(Long postId) {
        Long userId = 1L; //will get from context holder
        log.info("User with ID: {} Liking post with id {}", userId, postId);

        postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post with id " + postId + " not found"));
        boolean hasAlreadyLikes = postLikeRepository.existsByUserIdAndPostId(userId,postId);

        if(hasAlreadyLikes) throw new BadRequestException("Post with id " + postId + " is already liked, you cannot like the post again");

        // if the post ha snot been liked by user we craete a obj
        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLikeRepository.save(postLike);

        // ToDO: we can send notification to owner of post using Kafka
    }

    @Transactional // Ensures checking and deleting the like happen within one database transaction.
    public void unlikePost(Long postId) { // logging differently hence new method, could do in above method
        Long userId = 1L;
        log.info("User with ID: {} Unliking post with id {}", userId, postId);

        postRepository.findById(postId).orElseThrow(()
                ->new ResourceNotFoundException("Post with id " + postId + " not found"));
        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId); // Checks whether this user has liked the post.
        if (!hasAlreadyLiked) throw new BadRequestException("You cannot unlike the post that you have not yet liked"); // Rejects the request if no like exists.

        postLikeRepository.deleteByUserIdAndPostId(userId, postId); // Deletes the user's like record.

    }
}
