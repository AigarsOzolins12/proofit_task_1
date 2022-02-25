package com.proofit.task.controllers;

import com.proofit.task.dto.CalculationResult;
import com.proofit.task.dto.Trip;
import com.proofit.task.exceptions.InvalidValuesSuppliedException;
import com.proofit.task.service.TicketPriceCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebserviceController {
    private TicketPriceCalculatorService ticketPriceCalculatorService;
    private Logger logger = LoggerFactory.getLogger(WebserviceController.class);

    public WebserviceController(TicketPriceCalculatorService ticketPriceCalculatorService) {
        this.ticketPriceCalculatorService = ticketPriceCalculatorService;
    }

    @PostMapping("/calculate-ticket-price")
    public CalculationResult calculateTicketPrice(@RequestBody Trip trip) throws InvalidValuesSuppliedException {
        checkInputs(trip);

        return ticketPriceCalculatorService.calculateTicketPrice(trip);
    }

    private void checkInputs(Trip trip) {
        final String errorIdentifier = "ERROR:";

        if(trip == null || trip.getPassengers().isEmpty() || trip.getDestination().isEmpty() || trip.getStart().isEmpty()) {
            logger.error(errorIdentifier,new InvalidValuesSuppliedException("Values missing"));
            return;
        }

        if(trip.getPassengers().stream().anyMatch(passenger -> passenger.getPassengerType() == null)) {
            logger.error(errorIdentifier,new InvalidValuesSuppliedException("Passenger type missing"));
            return;
        }

        if(trip.getPassengers().stream().anyMatch(passenger -> passenger.getLuggage().stream().anyMatch(luggage -> luggage.getType() == null))) {
            logger.error(errorIdentifier,new InvalidValuesSuppliedException("Luggage type missing"));
        }
    }
}
