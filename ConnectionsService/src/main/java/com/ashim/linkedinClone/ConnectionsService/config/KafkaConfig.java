package com.ashim.linkedinClone.ConnectionsService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic  sendConnectionRequest() {
        return new NewTopic("connection_request_topic", 1, (short) 1); // set partition =3 later

    }

    @Bean
    public NewTopic acceptConnectionRequest() {
        return new NewTopic("connection_accepted_topic", 1, (short) 1);  // TOPIC_NAME, Partition, ReplicationFactor
    }
}


