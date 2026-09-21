package com.ashim.linkedinClone.notification_service.consumer;

import com.ashim.linkedinClone.ConnectionsService.event.ConnectionRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConnectionKafkaConsumer {
    @KafkaListener(topics = "connection_request_topic", groupId = "notification-service-group")
    public void handleConnectionRequest(ConnectionRequestEvent event) {
        log.info("Received connection request event for receiver ID: {}", event.getReceiverId());
        // Logic to save notification in DB or push via WebSocket to receiver
    }

    @KafkaListener(topics = "connection_accepted_topic", groupId = "notification-service-group")
    public void handleConnectionAccepted(ConnectionRequestEvent event) {
        log.info("Received connection accepted event for original sender ID: {}", event.getSenderId());
        // Logic to notify the sender that their request was accepted
    }
}
