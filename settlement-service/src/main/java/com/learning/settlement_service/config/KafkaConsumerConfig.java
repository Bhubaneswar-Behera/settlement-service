package com.learning.settlement_service.config;

import com.learning.common.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, PaymentEvent> consumerFactory() {

        JsonDeserializer<PaymentEvent> deserializer =
                new JsonDeserializer<>(PaymentEvent.class);
        deserializer.addTrustedPackages("*");

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "settlement-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentEvent>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, PaymentEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        // 🔥 ENABLE RETRY + BACKOFF
        factory.setCommonErrorHandler(errorHandler());

        return factory;
    }

    // 🚀 RETRY LOGIC (CORE PART)
    @Bean
    public DefaultErrorHandler errorHandler() {

        FixedBackOff backOff = new FixedBackOff(2000L, 3);

        // 👉 for now just log recovery (no DLT yet)
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (record, ex) -> {
                    log.error(
                            "❌ Final failure after retries | topic={} | partition={} | offset={} | value={} | error={}",
                            record.topic(),
                            record.partition(),
                            record.offset(),
                            record.value(),
                            ex.getMessage()
                    );
                },
                backOff
        );

        errorHandler.setRetryListeners((record, ex, deliveryAttempt) -> {
            log.warn(
                    "🔁 Retry attempt={} | topic={} | offset={} | value={}",
                    deliveryAttempt,
                    record.topic(),
                    record.offset(),
                    record.value()
            );
        });

        return errorHandler;
    }
}