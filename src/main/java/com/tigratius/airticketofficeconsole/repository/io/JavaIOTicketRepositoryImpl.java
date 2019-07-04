package main.java.com.tigratius.airticketofficeconsole.repository.io;

import main.java.com.tigratius.airticketofficeconsole.model.*;
import main.java.com.tigratius.airticketofficeconsole.repository.FlightRepository;
import main.java.com.tigratius.airticketofficeconsole.repository.TicketRepository;
import main.java.com.tigratius.airticketofficeconsole.util.IOUtil;

import java.util.*;

public class JavaIOTicketRepositoryImpl implements TicketRepository {

    private final static String FILE_NAME = "ticket.csv";
    private FlightRepository flightRepository;

    public JavaIOTicketRepositoryImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Ticket getById(Long id) throws Exception {
        List<Ticket> tickets = stringToData(IOUtil.read(FILE_NAME));
        Ticket current = null;
        for (Ticket ticket : tickets
        ) {
            if (ticket.getId().equals(id)) {
                current = ticket;
                break;
            }
        }

        if (current != null) {
            return current;
        }

        throw new Exception(Message.NOT_FIND_ID.getMessage() + id);
    }

    @Override
    public void delete(Ticket item) throws Exception {
        List<Ticket> tickets = stringToData(IOUtil.read(FILE_NAME));
        Ticket removeTicket = null;
        for (Ticket c : tickets
        ) {
            if (c.getId().equals(item.getId())) {
                removeTicket = c;
                break;
            }
        }
        tickets.remove(removeTicket);
        IOUtil.writeList(FILE_NAME, dataToString(tickets));
    }

    @Override
    public void update(Ticket item) throws Exception {
        delete(getById(item.getId()));
        save(item);
    }

    @Override
    public void save(Ticket item) {
        IOUtil.write(FILE_NAME, dataToString(item));
    }

    @Override
    public List<Ticket> getAll() throws Exception {
        return stringToData(IOUtil.read(FILE_NAME));
    }

    @Override
    public Long getLastId() throws Exception {
        List<Ticket> tickets = stringToData(IOUtil.read(FILE_NAME));
        tickets.sort(Comparator.comparing(BaseEntity::getId));

        if (tickets.size() != 0) {
            return tickets.get(tickets.size() - 1).getId();
        }

        return 0L;
    }

    @Override
    public List<Ticket> stringToData(List<String> items) throws Exception {
        List<Ticket> tickets = new ArrayList<>();

        for (String str : items
        ) {
            String[] parts = str.split(";");
            Ticket ticket = new Ticket();
            ticket.setId(Long.parseLong(parts[0]));

            ticket.setSeatType(SeatType.valueOf(parts[1]));

            Flight flight = flightRepository.getById(Long.parseLong(parts[2]));
            ticket.setFlight(flight);

            ticket.setFirstName(parts[3]);
            ticket.setLastName(parts[4]);

            tickets.add(ticket);
        }

        return tickets;
    }

    @Override
    public List<String> dataToString(List<Ticket> items) {
        List<String> data = new ArrayList<>();
        for (Ticket c : items) {
            data.add(dataToString(c));
        }
        return data;
    }

    @Override
    public String dataToString(Ticket ticket) {
        return ticket.getId() + ";" + ticket.getSeatType() + ";" + ticket.getFlight().getId() + ";"
                + ticket.getFirstName() + ";" + ticket.getLastName() + ";";
    }
}
