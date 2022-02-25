package com.proofit.task.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TripPriceRetrievalService {
    public BigDecimal getTripBasePrice(String busTerminalName) {
        return BigDecimal.valueOf(10L);
    }
}
