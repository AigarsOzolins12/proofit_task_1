package com.proofit.task.dto;

import com.proofit.task.enums.LuggageType;

public class Luggage {
    private LuggageType type;

    public Luggage() {
    }

    public Luggage(LuggageType type) {
        this.type = type;
    }

    public LuggageType getType() {
        return type;
    }

    public void setType(LuggageType type) {
        this.type = type;
    }
}
