package com.proofit.task.dto;

import java.util.List;

public class Trip {
    private String start;
    private String destination;
    private List<Passenger> passengers;

    public Trip(String start, String destination, List<Passenger> passengers) {
        this.start = start;
        this.destination = destination;
        this.passengers = passengers;
    }

    public String getStart() {
        return start;
    }

    public String getDestination() {
        return destination;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
}
