package com.learning.settlement_service.service;

import com.learning.common.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SettlementService {

    public void settle(PaymentEvent event) {

        log.info("💰 Settling payment: {}", event.getPaymentId());
        log.info("💵 Amount: {}", event.getAmount());
        log.info("💱 Currency: {}", event.getCurrency());

        // 🔥 TEST MODE: force failure for retry testing
        if (event.getAmount() != null && event.getAmount().intValue() > 1000) {
            log.warn("⚠️ Simulating failure for retry test");
            throw new RuntimeException("Simulated failure for retry testing");
        }

        // 🚀 SUCCESS FLOW
        log.info("✅ Settlement completed successfully for paymentId={}", event.getPaymentId());
    }
}