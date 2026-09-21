package com.ashim.linkedinClone.ConnectionsService.service;


import com.ashim.linkedinClone.ConnectionsService.auth.AuthContextHolder;
import com.ashim.linkedinClone.ConnectionsService.event.ConnectionRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsEventService {

    private final KafkaTemplate<Long, Object> kafkaTemplate;

    public void sendConnectionRequest(Long receiverId) {
        Long senderId = AuthContextHolder.getCurrentUserId();

        // Database logic to create connection request...

        ConnectionRequestEvent event = new ConnectionRequestEvent(senderId, receiverId);
        kafkaTemplate.send("connection_request_topic", receiverId, event);
        log.info("Published ConnectionRequestEvent to topic connection_request_topic");
    }

    public void acceptConnectionRequest(Long senderId) {
        Long receiverId = AuthContextHolder.getCurrentUserId();

        // Database logic to accept connection...

        ConnectionRequestEvent event = new ConnectionRequestEvent(senderId, receiverId);
        kafkaTemplate.send("connection_accepted_topic", senderId, event);
        log.info("Published ConnectionRequestEvent to topic connection_accepted_topic");
    }


}
