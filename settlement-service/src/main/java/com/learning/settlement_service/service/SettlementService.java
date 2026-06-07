package com.learning.settlement_service.service;

import com.learning.common.event.PaymentEvent;
import com.learning.settlement_service.entity.ProcessedPayment;
import com.learning.settlement_service.repository.ProcessedPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementService {

    private final ProcessedPaymentRepository repository;

    @Transactional
    public void settle(PaymentEvent event) {

        String paymentId = event.getPaymentId();

        // Idempotency Check
        if (repository.existsById(paymentId)) {

            log.warn(
                    "⚠️ Duplicate payment detected. Skipping. paymentId={}",
                    paymentId
            );

            return;
        }

        log.info(
                "💰 Processing payment. paymentId={}",
                paymentId
        );

        log.info(
                "💵 Amount={}",
                event.getAmount()
        );

        log.info(
                "💱 Currency={}",
                event.getCurrency()
        );

        // Simulate failure for retry testing
        if (event.getAmount() != null &&
                event.getAmount().intValue() > 1000) {

            log.error(
                    "❌ Simulated settlement failure. paymentId={}",
                    paymentId
            );

            throw new RuntimeException(
                    "Simulated failure for retry testing"
            );
        }

        repository.save(
                new ProcessedPayment(paymentId)
        );

        log.info(
                "✅ Settlement completed successfully. paymentId={}",
                paymentId
        );
    }
}