package com.ashim.linkedinClone.postsService.repository;

import com.ashim.linkedinClone.postsService.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);  //JPQl will find the method and create a query for us
}
