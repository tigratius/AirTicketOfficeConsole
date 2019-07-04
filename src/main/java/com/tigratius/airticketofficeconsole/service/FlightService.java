package main.java.com.tigratius.airticketofficeconsole.service;

import main.java.com.tigratius.airticketofficeconsole.model.Flight;
import main.java.com.tigratius.airticketofficeconsole.repository.FlightRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService {

    private FlightRepository flightRepository;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> findFlights(Date date, String departure, String arrival) throws Exception {

        List<Flight> flights = flightRepository.getAll();

        if (date != null) {
            flights = flights.stream().filter(flight -> sdf.format(flight.getRoute().getDepartureDate()).equals(sdf.format(date))).collect(Collectors.toList());
        }

        if (!departure.isEmpty()) {
            flights = flights.stream().filter(flight -> flight.getRoute().getDeparture().getName().toLowerCase().contains(departure.toLowerCase())).collect(Collectors.toList());
        }

        if (!arrival.isEmpty()) {
            flights = flights.stream().filter(flight -> flight.getRoute().getArrival().getName().toLowerCase().contains(arrival.toLowerCase())).collect(Collectors.toList());
        }

        return flights;
    }

    public List<Flight> getFlights() throws Exception {
        return flightRepository.getAll();
    }
}
