package com.proofit.task.service;

import com.proofit.task.dto.*;
import com.proofit.task.enums.LuggageType;
import com.proofit.task.enums.PassengerType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketPriceCalculatorServiceTest {
    private TicketPriceCalculatorService ticketPriceCalculatorService;

    private Trip testTrip;

    @Before
    public void setup() {
        TaxRateRetrievalService taxRateRetrievalService = Mockito.mock(TaxRateRetrievalService.class);
        TripPriceRetrievalService tripPriceRetrievalService = Mockito.mock(TripPriceRetrievalService.class);

        Mockito.when(taxRateRetrievalService.getTaxRateForDay(Mockito.any())).thenReturn(BigDecimal.valueOf(0.21));
        Mockito.when(tripPriceRetrievalService.getTripBasePrice(Mockito.any())).thenReturn(BigDecimal.valueOf(10L));

        ticketPriceCalculatorService = new TicketPriceCalculatorService(taxRateRetrievalService, tripPriceRetrievalService);

        List<Luggage> adultLuggage = Arrays.asList(new Luggage(LuggageType.BAG), new Luggage(LuggageType.BAG));
        List<Luggage> childLuggage = Collections.singletonList(new Luggage(LuggageType.BAG));
        List<Passenger> passengers = Arrays.asList(new Passenger(adultLuggage, PassengerType.ADULT),
                new Passenger(childLuggage, PassengerType.CHILD));

        testTrip = new Trip("Riga", "Vilnius", passengers);
    }

    @Test
    public void shouldHaveCorrectTotalPrice_WhenOneAdultAndOneChildWithLuggage() {
        CalculationResult ticketPrice = ticketPriceCalculatorService.calculateTicketPrice(testTrip);

        Assert.assertEquals(29.04, ticketPrice.getTotal().doubleValue(), 0.01);
    }

    @Test
    public void shouldHaveCorrectTotalPrice_WhenOneAdultNoLuggage() {
        Trip newTrip = testTrip;
        newTrip.setPassengers(Collections.singletonList(new Passenger(new ArrayList<>(), PassengerType.ADULT)));

        CalculationResult ticketPrice = ticketPriceCalculatorService.calculateTicketPrice(newTrip);

        Assert.assertEquals(12.10, ticketPrice.getTotal().doubleValue(), 0.01);
    }

    @Test
    public void shouldHaveCorrectTotalPrice_WhenOneAdultAndOneChildNoLuggage() {
        Trip newTrip = testTrip;
        newTrip.setPassengers(Arrays.asList(new Passenger(new ArrayList<>(), PassengerType.ADULT),
                new Passenger(new ArrayList<>(), PassengerType.CHILD)));

        CalculationResult ticketPrice = ticketPriceCalculatorService.calculateTicketPrice(newTrip);

        Assert.assertEquals(18.15, ticketPrice.getTotal().doubleValue(), 0.01);
    }

    @Test
    public void shouldHaveCorrectTotalPrice_WhenOneChildNoLuggage() {
        Trip newTrip = testTrip;
        newTrip.setPassengers(Collections.singletonList(new Passenger(new ArrayList<>(), PassengerType.CHILD)));

        CalculationResult ticketPrice = ticketPriceCalculatorService.calculateTicketPrice(newTrip);

        Assert.assertEquals(6.05, ticketPrice.getTotal().doubleValue(), 0.01);
    }

    @Test
    public void shouldHaveCorrectTotalPrice_WhenOneAdultAndLuggage() {
        Trip newTrip = testTrip;
        newTrip.setPassengers(Collections.singletonList(
                new Passenger(
                        Collections.singletonList(new Luggage(LuggageType.BAG)),
                        PassengerType.ADULT
                )
        ));

        CalculationResult ticketPrice = ticketPriceCalculatorService.calculateTicketPrice(newTrip);

        Assert.assertEquals(15.73, ticketPrice.getTotal().doubleValue(), 0.01);
    }

    @Test
    public void shouldHaveCorrectTotalPrice_WhenOneChildAndLuggage() {
        Trip newTrip = testTrip;
        newTrip.setPassengers(Collections.singletonList(
                new Passenger(
                        Collections.singletonList(new Luggage(LuggageType.BAG)),
                        PassengerType.CHILD
                )
        ));

        CalculationResult ticketPrice = ticketPriceCalculatorService.calculateTicketPrice(newTrip);

        Assert.assertEquals(9.68, ticketPrice.getTotal().doubleValue(), 0.01);
    }

    @Test
    public void shouldHaveCorrectTotalPrice_WhenTwoAdultsAndTwoChildrenAndLuggage() {
        Trip newTrip = testTrip;
        newTrip.setPassengers(Arrays.asList(
                new Passenger(
                        Arrays.asList(new Luggage(LuggageType.BAG), new Luggage(LuggageType.BAG)),
                        PassengerType.ADULT),
                new Passenger(
                        Arrays.asList(new Luggage(LuggageType.BAG), new Luggage(LuggageType.BAG)),
                        PassengerType.ADULT),
                new Passenger(
                        Collections.singletonList(new Luggage(LuggageType.BAG)),
                        PassengerType.CHILD),
                new Passenger(Collections.singletonList(new Luggage(LuggageType.BAG)),
                        PassengerType.CHILD))
        );

        CalculationResult ticketPrice = ticketPriceCalculatorService.calculateTicketPrice(newTrip);

        Assert.assertEquals(58.08, ticketPrice.getTotal().doubleValue(), 0.01);
    }

    @Test
    public void shouldHaveCorrectSubtotal_WhenOneAdultOneLuggage() {
        Trip newTrip = testTrip;
        newTrip.setPassengers(Collections.singletonList(new Passenger(Collections.singletonList(new Luggage(LuggageType.BAG)), PassengerType.ADULT)));

        CalculationResult calculationResult = ticketPriceCalculatorService.calculateTicketPrice(newTrip);
        List<Subtotal> subtotalList = calculationResult.getSubtotalList();

        Subtotal subtotal = subtotalList.get(0);
        Assert.assertEquals(3.63, subtotal.getLuggageSubTotals().get(0).getAmount().doubleValue(), 0.01);

        Assert.assertEquals(12.10, subtotal.getPassengerTicketPrice().doubleValue(), 0.01);
    }

    @Test
    public void shouldHaveCorrectSubtotal_WhenOneAdultTwoLuggage() {
        Trip newTrip = testTrip;
        newTrip.setPassengers(Collections.singletonList(new Passenger(Arrays.asList(new Luggage(LuggageType.BAG), new Luggage(LuggageType.BAG)), PassengerType.ADULT)));

        CalculationResult calculationResult = ticketPriceCalculatorService.calculateTicketPrice(newTrip);
        List<Subtotal> subtotalList = calculationResult.getSubtotalList();

        Subtotal subtotal = subtotalList.get(0);

        Assert.assertEquals(3.63, subtotal.getLuggageSubTotals().get(0).getAmount().doubleValue(), 0.01);
        Assert.assertEquals(3.63, subtotal.getLuggageSubTotals().get(1).getAmount().doubleValue(), 0.01);

        Assert.assertEquals(12.10, subtotal.getPassengerTicketPrice().doubleValue(), 0.01);
    }

    @Test
    public void shouldHaveCorrectSubtotal_WhenTwoAdultTwoLuggage() {
        Trip newTrip = testTrip;
        newTrip.setPassengers(Arrays.asList(
                new Passenger(Arrays.asList(new Luggage(LuggageType.BAG), new Luggage(LuggageType.BAG)), PassengerType.ADULT),
                new Passenger(Arrays.asList(new Luggage(LuggageType.BAG), new Luggage(LuggageType.BAG)), PassengerType.ADULT)
        ));

        CalculationResult calculationResult = ticketPriceCalculatorService.calculateTicketPrice(newTrip);
        List<Subtotal> subtotalList = calculationResult.getSubtotalList();

        Subtotal firstPersonSubtotal = subtotalList.get(0);

        Assert.assertEquals(3.63, firstPersonSubtotal.getLuggageSubTotals().get(0).getAmount().doubleValue(), 0.01);
        Assert.assertEquals(3.63, firstPersonSubtotal.getLuggageSubTotals().get(1).getAmount().doubleValue(), 0.01);

        Assert.assertEquals(12.10, firstPersonSubtotal.getPassengerTicketPrice().doubleValue(), 0.01);

        Subtotal secondPersonSubtotal = subtotalList.get(1);

        Assert.assertEquals(3.63, secondPersonSubtotal.getLuggageSubTotals().get(0).getAmount().doubleValue(), 0.01);
        Assert.assertEquals(3.63, secondPersonSubtotal.getLuggageSubTotals().get(1).getAmount().doubleValue(), 0.01);

        Assert.assertEquals(12.10, secondPersonSubtotal.getPassengerTicketPrice().doubleValue(), 0.01);
    }

    @Test
    public void shouldHaveCorrectSubtotal_WhenOneAdultOneChildTwoLuggage() {
        Trip newTrip = testTrip;
        newTrip.setPassengers(Arrays.asList(
                new Passenger(Arrays.asList(new Luggage(LuggageType.BAG), new Luggage(LuggageType.BAG)), PassengerType.ADULT),
                new Passenger(Arrays.asList(new Luggage(LuggageType.BAG), new Luggage(LuggageType.BAG)), PassengerType.CHILD)
        ));

        CalculationResult calculationResult = ticketPriceCalculatorService.calculateTicketPrice(newTrip);
        List<Subtotal> subtotalList = calculationResult.getSubtotalList();

        Subtotal firstPersonSubtotal = subtotalList.get(0);

        Assert.assertEquals(3.63, firstPersonSubtotal.getLuggageSubTotals().get(0).getAmount().doubleValue(), 0.01);
        Assert.assertEquals(3.63, firstPersonSubtotal.getLuggageSubTotals().get(1).getAmount().doubleValue(), 0.01);

        Assert.assertEquals(12.10, firstPersonSubtotal.getPassengerTicketPrice().doubleValue(), 0.01);

        Subtotal secondPersonSubtotal = subtotalList.get(1);

        Assert.assertEquals(3.63, secondPersonSubtotal.getLuggageSubTotals().get(0).getAmount().doubleValue(), 0.01);
        Assert.assertEquals(3.63, secondPersonSubtotal.getLuggageSubTotals().get(1).getAmount().doubleValue(), 0.01);

        Assert.assertEquals(6.05, secondPersonSubtotal.getPassengerTicketPrice().doubleValue(), 0.01);
    }
}
