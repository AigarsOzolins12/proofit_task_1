package com.proofit.task.service;

import com.proofit.task.dto.*;
import com.proofit.task.enums.Discountable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TicketPriceCalculatorService {
    private final TaxRateRetrievalService taxRateRetrievalService;
    private final TripPriceRetrievalService tripPriceRetrievalService;

    public TicketPriceCalculatorService(TaxRateRetrievalService taxRateRetrievalService, TripPriceRetrievalService tripPriceRetrievalService) {
        this.taxRateRetrievalService = taxRateRetrievalService;
        this.tripPriceRetrievalService = tripPriceRetrievalService;
    }

    public CalculationResult calculateTicketPrice(Trip trip) {
        BigDecimal baseTicketPrice = tripPriceRetrievalService.getTripBasePrice(trip.getStart() + " - " + trip.getDestination());
        BigDecimal vatTax = taxRateRetrievalService.getTaxRateForDay(LocalDate.now());

        CalculationResult calculationResult = new CalculationResult();
        calculationResult.setSubtotalList(trip.getPassengers().stream().map(passenger ->  {
            BigDecimal passengerTicketPrice = calculatePriceForDiscountableItem(passenger.getPassengerType(), baseTicketPrice, vatTax);

            List<LuggageSubTotal> luggageSubTotals = passenger
                    .getLuggage()
                    .stream()
                    .map(luggage -> {
                        BigDecimal amount = calculatePriceForDiscountableItem(luggage.getType(), baseTicketPrice, vatTax);
                        return new LuggageSubTotal(luggage.getType(), amount);
                    })
                    .collect(Collectors.toList());

            calculationResult.setTotal(calculationResult.getTotal()
                    .add(passengerTicketPrice)
                    .add(luggageSubTotals.stream().map(LuggageSubTotal::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add))
            );

            return new Subtotal(passenger.getPassengerType(), passengerTicketPrice, luggageSubTotals);
        }).collect(Collectors.toList()));

        return calculationResult;
    }

    private BigDecimal calculatePriceForDiscountableItem(Discountable type, BigDecimal baseTicketPrice, BigDecimal vatTax) {
        return addVatTaxToAmount(baseTicketPrice.multiply(type.getPercentageOfBasePrice()), vatTax);
    }

    private BigDecimal addVatTaxToAmount(BigDecimal amount, BigDecimal vatTax) {
        return amount.add(amount.multiply(vatTax));
    }
}
