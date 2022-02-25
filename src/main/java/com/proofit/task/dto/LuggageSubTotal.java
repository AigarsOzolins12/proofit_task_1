package com.proofit.task.dto;

import com.proofit.task.enums.Discountable;

import java.math.BigDecimal;

public class LuggageSubTotal {
    private Discountable luggageType;
    private BigDecimal amount;

    public LuggageSubTotal(Discountable luggageType, BigDecimal amount) {
        this.luggageType = luggageType;
        this.amount = amount;
    }

    public LuggageSubTotal() {
    }

    public Discountable getLuggageType() {
        return luggageType;
    }

    public void setLuggageType(Discountable luggageType) {
        this.luggageType = luggageType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
