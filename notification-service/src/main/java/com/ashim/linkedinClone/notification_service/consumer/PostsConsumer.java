package com.ashim.linkedinClone.notification_service.consumer;

import com.ashim.linkedinClone.notification_service.entity.Notification;
import com.ashim.linkedinClone.notification_service.service.NotificationService;
import com.ashim.linkedinClone.postsService.event.PostCreated;
import com.ashim.linkedinClone.postsService.event.PostLiked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "post_created_topic")
    public void handlePostCreated(PostCreated postCreatedEvent){
       // System.out.println("Received post created event for post ID: " + postCreatedEvent.getPostId());
        log.info("handledPostCreated: {} ", postCreatedEvent);

        String message = String.format("Your connection with id: %d has created this post: %s",
                postCreatedEvent.getOwnerUserId(), postCreatedEvent.getContent());

        Notification notification = Notification.builder()
                .messages(message)
                .userId(postCreatedEvent.getUserId())
                .build();

        notificationService.addNotification(notification);

    }


    @KafkaListener(topics = "post_liked_topic")
    public void handlePostLiked(PostLiked postLikedEvent){

        log.info("handledPostLiked: {} ", postLikedEvent);
        String message = String.format("User with id:: %d has liked your post with id: %d",
                postLikedEvent.getLikedByUserId(), postLikedEvent.getPostId());

        Notification notification = Notification.builder()
                .messages(message)
                .userId(postLikedEvent.getOwnerUserId())
                .build();

        notificationService.addNotification(notification);

    }
}

/*
Your post_liked_topic has multiple partitions (e.g., 3 partitions), but when you run multiple listener methods or consumers in the same application,
 partition assignment can get lopsided or stuck depending on how @KafkaListener concurrency is configured.
 */