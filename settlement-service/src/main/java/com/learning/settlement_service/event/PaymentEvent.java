package com.learning.settlement_service.event;

import java.math.BigDecimal;

public record PaymentEvent(
        String paymentId,
        BigDecimal amount,
        String currency,
        String status
) {
}