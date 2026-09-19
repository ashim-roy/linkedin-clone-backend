package com.ashim.linkedinClone.notification_service.config;

import com.ashim.linkedinClone.postsService.event.PostCreated;
import com.ashim.linkedinClone.postsService.event.PostLiked;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<Long, PostCreated> postCreatedConsumerFactory(
            KafkaProperties kafkaProperties) {

        Map<String, Object> props =
                kafkaProperties.buildConsumerProperties();

        JacksonJsonDeserializer<PostCreated> deserializer =
                new JacksonJsonDeserializer<>(PostCreated.class);

        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new LongDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConsumerFactory<Long, PostLiked> postLikedConsumerFactory(
            KafkaProperties kafkaProperties) {

        Map<String, Object> props =
                kafkaProperties.buildConsumerProperties();

        JacksonJsonDeserializer<PostLiked> deserializer =
                new JacksonJsonDeserializer<>(PostLiked.class);

        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new LongDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, PostCreated>
    postCreatedKafkaListenerContainerFactory(
            ConsumerFactory<Long, PostCreated> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<Long, PostCreated> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, PostLiked>
    postLikedKafkaListenerContainerFactory(
            ConsumerFactory<Long, PostLiked> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<Long, PostLiked> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}
