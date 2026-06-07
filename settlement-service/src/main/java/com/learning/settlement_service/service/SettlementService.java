package com.learning.settlement_service.service;

import com.learning.settlement_service.common.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SettlementService {

    public void settle(PaymentEvent event) {

        log.info("💰 Settling payment: {}", event.getPaymentId());
        log.info("💵 Amount: {}", event.getAmount());
        log.info("💱 Currency: {}", event.getCurrency());

        log.info("✅ Settlement completed");
    }
}