package com.learning.settlement_service.controller;

import com.learning.settlement_service.entity.ProcessedPayment;
import com.learning.settlement_service.repository.ProcessedPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DbController {

    private final ProcessedPaymentRepository repository;

    @GetMapping("/payments")
    public List<ProcessedPayment> getPayments() {
        return repository.findAll();
    }
}
