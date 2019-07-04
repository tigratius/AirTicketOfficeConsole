package main.java.com.tigratius.airticketofficeconsole.controller;

import main.java.com.tigratius.airticketofficeconsole.model.Flight;
import main.java.com.tigratius.airticketofficeconsole.service.FlightService;

import java.util.Date;
import java.util.List;

public class FlightController {

    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    public List<Flight> findFlights(Date date, String departure, String arrival) throws Exception {
        return flightService.findFlights(date, departure, arrival);
    }

    public List<Flight> getFlights() throws Exception {
        return flightService.getFlights();
    }
}
