package com.ashim.linkedinClone.postsService.config;

import com.ashim.linkedinClone.postsService.event.PostCreated;
import com.ashim.linkedinClone.postsService.event.PostLiked;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic postCreated() {
        return new NewTopic("post_created_topic", 1, (short) 1); // set partition =3 later

    }

    @Bean
    public NewTopic postLiked() {
        return new NewTopic("post_liked_topic", 1, (short) 1);  // TOPIC_NAME, Partition, ReplicationFactor
    }
}

//    // Explicit KafkaTemplate for PostCreated events
//    @Bean
//    public ProducerFactory<Long, PostCreated> postCreatedProducerFactory(KafkaProperties kafkaProperties) {
//        Map<String, Object> props = kafkaProperties.buildProducerProperties(null);
//        JsonSerializer<PostCreated> jsonSerializer = new JsonSerializer<>();
//        jsonSerializer.setAddTypeInfo(false);
//        return new DefaultKafkaProducerFactory<>(props, new LongSerializer(), jsonSerializer);
//    }
//
//    @Bean
//    public KafkaTemplate<Long, PostCreated> postCreatedKafkaTemplate(ProducerFactory<Long, PostCreated> producerFactory) {
//        return new KafkaTemplate<>(producerFactory);
//    }
//
//    @Bean
//    public ProducerFactory<Long, PostLiked> postLikeProducerFactory(KafkaProperties kafkaProperties) {
//        Map<String, Object> props = kafkaProperties.buildProducerProperties(null);
//        JsonSerializer<PostLiked> jsonSerializer = new JsonSerializer<>();
//        jsonSerializer.setAddTypeInfoHeader(false); // Prevents header mismatch issues
//        return new DefaultKafkaProducerFactory<>(props, new LongSerializer(), jsonSerializer);
//    }
//
//    @Bean
//    public KafkaTemplate<Long, PostLiked> postLikeKafkaTemplate(ProducerFactory<Long, PostLiked> producerFactory) {
//        return new KafkaTemplate<>(producerFactory);
//    }


