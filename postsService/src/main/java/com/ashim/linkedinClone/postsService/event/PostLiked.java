package com.ashim.linkedinClone.postsService.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor   // <-- Required for Jackson deserialization
@AllArgsConstructor  // <-- Required for the @Builder pattern
public class PostLiked {

    private Long postId;
    private Long ownerUserId;
    private Long likedByUserId;

}
