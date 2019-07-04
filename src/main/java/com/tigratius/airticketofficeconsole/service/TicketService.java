package main.java.com.tigratius.airticketofficeconsole.service;

import main.java.com.tigratius.airticketofficeconsole.model.Flight;
import main.java.com.tigratius.airticketofficeconsole.model.SeatType;
import main.java.com.tigratius.airticketofficeconsole.model.Ticket;
import main.java.com.tigratius.airticketofficeconsole.repository.FlightRepository;
import main.java.com.tigratius.airticketofficeconsole.repository.TicketRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TicketService {

    private FlightRepository flightRepository;
    private TicketRepository ticketRepository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final String noSeatsMessage = "Нет свободных мест!";

    public TicketService(FlightRepository flightRepository, TicketRepository ticketRepository) {
        this.flightRepository = flightRepository;
        this.ticketRepository = ticketRepository;
    }

    public void buyTicket(Long flightId, SeatType seatType, String firstName, String lastName) throws Exception {

        Flight flight = flightRepository.getById(flightId);

        if (!flight.occupySeat(seatType))
            throw new Exception(noSeatsMessage);

        Ticket ticket = new Ticket();
        ticket.setId(ticketRepository.getLastId() + 1);
        ticket.setSeatType(seatType);
        ticket.setFlight(flight);
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);

        flightRepository.update(flight);
        ticketRepository.save(ticket);
    }

    public void returnTicket(Long ticketId) throws Exception {

        Ticket ticket = ticketRepository.getById(ticketId);
        Flight flight = ticket.getFlight();

        flight.reassignSeat(ticket.getSeatType());

        flightRepository.update(flight);
        ticketRepository.delete(ticket);
    }

    public List<Ticket> findTicket(Date date, String from, String to, SeatType seatType) throws Exception {

        List<Ticket> tickets = ticketRepository.getAll();

        if (date != null) {
            tickets= tickets.stream().filter(ticket -> sdf.format(ticket.getFlight().getRoute().getDepartureDate()).equals(sdf.format(date))).collect(Collectors.toList());
        }

        if (!from.isEmpty()) {
            tickets = tickets.stream().filter(ticket -> ticket.getFlight().getRoute().getDeparture().getName().toLowerCase().contains(from.toLowerCase())).collect(Collectors.toList());
        }

        if (!to.isEmpty()) {
            tickets = tickets.stream().filter(ticket -> ticket.getFlight().getRoute().getArrival().getName().toLowerCase().contains(to.toLowerCase())).collect(Collectors.toList());
        }

        if (seatType != null) {
            tickets = tickets.stream().filter((ticket -> ticket.getSeatType() == seatType)).collect(Collectors.toList());
        }

        return tickets;
    }

    public List<Ticket> getTickets() throws Exception {
        return ticketRepository.getAll();
    }
}
