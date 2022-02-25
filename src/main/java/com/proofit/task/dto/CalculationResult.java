package com.proofit.task.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculationResult {
    List<Subtotal> subtotalList;
    BigDecimal total;

    public CalculationResult(List<Subtotal> subtotalList, BigDecimal total) {
        this.subtotalList = subtotalList;
        this.total = total;
    }

    public CalculationResult() {
        this.subtotalList = new ArrayList<>();
        this.total = BigDecimal.ZERO;
    }

    public List<Subtotal> getSubtotalList() {
        return subtotalList;
    }

    public void setSubtotalList(List<Subtotal> subtotalList) {
        this.subtotalList = subtotalList;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
