package com.ashimCS.linkedinClone.postsService.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDto {

    private Long id;
    private String content;
    private long userId;
    private LocalDateTime createdAt;
}
