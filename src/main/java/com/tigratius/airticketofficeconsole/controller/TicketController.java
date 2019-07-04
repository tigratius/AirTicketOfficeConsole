package main.java.com.tigratius.airticketofficeconsole.controller;

import main.java.com.tigratius.airticketofficeconsole.model.SeatType;
import main.java.com.tigratius.airticketofficeconsole.model.Ticket;
import main.java.com.tigratius.airticketofficeconsole.service.TicketService;

import java.util.Date;
import java.util.List;

public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void buyTicket(Long flightId, SeatType seatType, String firstName, String lastName) throws Exception {
        ticketService.buyTicket(flightId, seatType, firstName, lastName);
    }

    public void returnTicket(Long ticketId) throws Exception {
        ticketService.returnTicket(ticketId);
    }

    public List<Ticket> findTicket(Date date, String from, String to, SeatType seatType) throws Exception {
        return ticketService.findTicket(date, from, to, seatType);
    }

    public List<Ticket> getTickets() throws Exception {
        return ticketService.getTickets();
    }
}
