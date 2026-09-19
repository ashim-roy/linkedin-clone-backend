package com.ashim.linkedinClone.postsService.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor   // <-- Required by Jackson for deserialization
@AllArgsConstructor  // <-- Required by @Builder when used together
public class PostCreated {

    private Long ownerUserId;
    private Long postId;
    private Long userId;
    private String content; // we can set small section of post but we will set everything entire ppost

}
