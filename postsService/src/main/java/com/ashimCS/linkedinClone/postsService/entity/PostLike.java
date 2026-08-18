package com.ashimCS.linkedinClone.postsService.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "post_likes")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private long postId;

    @CreationTimestamp
    private LocalDateTime createdDate;
}
