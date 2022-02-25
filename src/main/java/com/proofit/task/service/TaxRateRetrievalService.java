package com.proofit.task.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TaxRateRetrievalService {
    public BigDecimal getTaxRateForDay(LocalDate date) {
        return BigDecimal.valueOf(0.21);
    }
}
