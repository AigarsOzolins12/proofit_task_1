package com.proofit.task.enums;

import java.math.BigDecimal;

public enum PassengerType implements Discountable {
    CHILD("Child", BigDecimal.valueOf(0.5)),
    ADULT("Adult", BigDecimal.valueOf(1));

    public final String value;
    public final BigDecimal percentageOfBasePrice;

    PassengerType(String value, BigDecimal percentageOfBasePrice) {
        this.value = value;
        this.percentageOfBasePrice = percentageOfBasePrice;
    }

    public String getValue() {
        return value;
    }

    public BigDecimal getPercentageOfBasePrice() {
        return percentageOfBasePrice;
    }
}
