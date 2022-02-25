package com.proofit.task.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proofit.task.ProofitApplication;
import com.proofit.task.dto.Luggage;
import com.proofit.task.dto.Passenger;
import com.proofit.task.dto.Trip;
import com.proofit.task.enums.LuggageType;
import com.proofit.task.enums.PassengerType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebserviceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test()
    public void shouldHaveSumsCalculated_WhenTripInputted() throws Exception {
        Trip trip = new Trip("Riga","Vilnius", Arrays.asList(
                new Passenger(Arrays.asList(new Luggage(LuggageType.BAG), new Luggage(LuggageType.BAG)), PassengerType.ADULT),
                new Passenger(Collections.singletonList(new Luggage(LuggageType.BAG)), PassengerType.CHILD)
        ));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(trip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mockMvc.perform(post("/calculate-ticket-price").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total").value("29.04"))
                .andExpect(jsonPath("$.subtotalList[0].passengerTicketPrice").value("12.1"));

    }
}
