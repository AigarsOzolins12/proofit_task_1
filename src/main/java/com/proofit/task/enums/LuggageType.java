package com.proofit.task.enums;

import java.math.BigDecimal;

public enum LuggageType implements Discountable {
    BAG("Bag", BigDecimal.valueOf(0.30));

    public final String value;
    public final BigDecimal percentageOfBasePrice;

    LuggageType(String value, BigDecimal percentageOfBasePrice) {
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
