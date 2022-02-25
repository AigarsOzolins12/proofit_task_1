package com.proofit.task.dto;

import com.proofit.task.enums.PassengerType;

import java.math.BigDecimal;
import java.util.List;

public class Subtotal {
    private PassengerType passengerType;
    private BigDecimal passengerTicketPrice;
    private List<LuggageSubTotal> luggageSubTotals;

    public Subtotal(PassengerType passengerType, BigDecimal passengerTicketPrice, List<LuggageSubTotal> luggageSubTotals) {
        this.passengerType = passengerType;
        this.passengerTicketPrice = passengerTicketPrice;
        this.luggageSubTotals = luggageSubTotals;
    }

    public Subtotal() {
    }

    public PassengerType getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(PassengerType passengerType) {
        this.passengerType = passengerType;
    }

    public BigDecimal getPassengerTicketPrice() {
        return passengerTicketPrice;
    }

    public void setPassengerTicketPrice(BigDecimal passengerTicketPrice) {
        this.passengerTicketPrice = passengerTicketPrice;
    }

    public List<LuggageSubTotal> getLuggageSubTotals() {
        return luggageSubTotals;
    }

    public void setLuggageSubTotals(List<LuggageSubTotal> luggageSubTotals) {
        this.luggageSubTotals = luggageSubTotals;
    }
}
