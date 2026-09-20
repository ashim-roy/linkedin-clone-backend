package com.ashim.linkedinClone.ConnectionsService.consumer;

import com.ashim.linkedinClone.ConnectionsService.service.MemberService;
import com.ashim.linkedinClone.userService.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceConsumer {

    private final MemberService memberService;

    // consumer the event

    @KafkaListener(topics = "user_created_topic")
    public void handleMemberCreated(UserCreatedEvent userCreatedEvent) {
        log.info("handleMemberCreated - Received user_created_event {}", userCreatedEvent.getName());

        // when we receive the event we will call memberService to save it
        memberService.createMember(userCreatedEvent.getUserId(), userCreatedEvent.getName());
    }
}
