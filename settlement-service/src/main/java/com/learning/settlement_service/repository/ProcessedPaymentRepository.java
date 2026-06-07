package com.learning.settlement_service.repository;

import com.learning.settlement_service.entity.ProcessedPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedPaymentRepository
        extends JpaRepository<ProcessedPayment, String> {
}