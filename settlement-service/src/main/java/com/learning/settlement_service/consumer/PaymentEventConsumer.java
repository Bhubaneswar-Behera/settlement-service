package com.learning.settlement_service.consumer;

import com.learning.settlement_service.service.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final SettlementService settlementService;

    @KafkaListener(topics = "payment-events", groupId = "settlement-group")
    public void consume(String message) {

        log.info("🔥 Received Kafka message: {}", message);

        settlementService.settlePayment(message);
    }
}