package com.proofit.task.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TripPriceRetrievalServiceTest {
    @Autowired
    private TripPriceRetrievalService tripPriceRetrievalService;

    @Test
    public void shouldReturnBasePrice_WhenBusTerminalNameSupplied() {
        BigDecimal basePrice = tripPriceRetrievalService.getTripBasePrice("Riga - Vilnus");

        Assert.assertEquals(10.00, basePrice.doubleValue(), 0.01);
    }
}
