package com.learning.settlement_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_payments")
public class ProcessedPayment {

    @Id
    private String paymentId;

    private LocalDateTime processedAt;

    public ProcessedPayment() {
    }

    public ProcessedPayment(String paymentId) {
        this.paymentId = paymentId;
        this.processedAt = LocalDateTime.now();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}