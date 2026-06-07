package com.learning.settlement_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SettlementService {

    public void settlePayment(String message) {

        log.info("💰 Starting settlement process");

        log.info("📩 Received payment event: {}", message);

        // business logic simulation
        log.info("🔄 Processing payment settlement...");

        // simulate success
        log.info("✅ Payment settlement completed successfully");
    }
}