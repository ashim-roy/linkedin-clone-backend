package com.ashim.linkedinClone.postsService.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor   // <-- Required by Jackson for deserialization
@AllArgsConstructor  // <-- Required by @Builder when used together
public class PostLiked {

    private Long postId;
    private Long ownerUserId;
    private Long likedByUserId;

}
