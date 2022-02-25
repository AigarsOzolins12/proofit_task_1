package com.proofit.task.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaxRateRetrievalServiceTest {
    @Autowired
    private TaxRateRetrievalService taxRateRetrievalService;

    @Test
    public void shouldReturnTax_whenDateSupplied() {
        BigDecimal vatTax = taxRateRetrievalService.getTaxRateForDay(LocalDate.now());
        Assert.assertEquals(0.21, vatTax.doubleValue(), 0.01);
    }
}
