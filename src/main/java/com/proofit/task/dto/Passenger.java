package com.proofit.task.dto;

import com.proofit.task.enums.PassengerType;

import java.util.List;

public class Passenger  {
    private List<Luggage> luggage;
    private PassengerType passengerType;

    public Passenger() {
    }

    public Passenger(List<Luggage> luggage, PassengerType passengerType) {
        this.luggage = luggage;
        this.passengerType = passengerType;
    }

    public List<Luggage> getLuggage() {
        return luggage;
    }

    public void setLuggage(List<Luggage> luggage) {
        this.luggage = luggage;
    }

    public PassengerType getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(PassengerType calculationType) {
        this.passengerType = calculationType;
    }
}
