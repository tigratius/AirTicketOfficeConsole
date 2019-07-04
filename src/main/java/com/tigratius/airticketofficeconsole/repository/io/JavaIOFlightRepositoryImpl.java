package main.java.com.tigratius.airticketofficeconsole.repository.io;

import main.java.com.tigratius.airticketofficeconsole.model.*;
import main.java.com.tigratius.airticketofficeconsole.repository.AircraftRepository;
import main.java.com.tigratius.airticketofficeconsole.repository.FlightRepository;
import main.java.com.tigratius.airticketofficeconsole.repository.RouteRepository;
import main.java.com.tigratius.airticketofficeconsole.util.IOUtil;

import java.util.*;

public class JavaIOFlightRepositoryImpl implements FlightRepository {
    private final static String FILE_NAME = "flight.csv";

    private AircraftRepository aircraftRepository;
    private RouteRepository routeRepository;


    public JavaIOFlightRepositoryImpl(AircraftRepository aircraftRepository, RouteRepository routeRepository) {
        this.aircraftRepository = aircraftRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public Flight getById(Long id) throws Exception {
        List<Flight> flights = stringToData(IOUtil.read(FILE_NAME));

        Flight current = null;
        for (Flight flight : flights
        ) {
            if (flight.getId().equals(id)) {
                current = flight;
                break;
            }
        }

        if (current != null) {
            return current;
        }

        throw new Exception(Message.NOT_FIND_ID.getMessage() + id);
    }

    @Override
    public void delete(Flight item) throws Exception {
        List<Flight> flights = stringToData(IOUtil.read(FILE_NAME));
        Flight removeFlight = null;
        for (Flight c : flights
        ) {
            if (c.getId().equals(item.getId())) {
                removeFlight = c;
                break;
            }
        }
        flights.remove(removeFlight);
        IOUtil.writeList(FILE_NAME, dataToString(flights));
    }

    @Override
    public void update(Flight item) throws Exception {
        delete(getById(item.getId()));
        save(item);
    }

    @Override
    public void save(Flight item) {
        IOUtil.write(FILE_NAME, dataToString(item));
    }

    @Override
    public List<Flight> getAll() throws Exception {
        return stringToData(IOUtil.read(FILE_NAME));
    }

    @Override
    public Long getLastId() throws Exception {
        List<Flight> flights = stringToData(IOUtil.read(FILE_NAME));
        flights.sort(Comparator.comparing(BaseEntity::getId));

        if (flights.size() != 0) {
            return flights.get(flights.size() - 1).getId();
        }

        return 0L;
    }

    @Override
    public List<Flight> stringToData(List<String> items) throws Exception {
        List<Flight> flights = new ArrayList<>();

        for (String str : items
        ) {
            String[] parts = str.split(";");
            Flight flight = new Flight();
            flight.setId(Long.parseLong(parts[0]));


            Aircraft aircraft = aircraftRepository.getById(Long.parseLong(parts[1]));
            flight.setAircraft(aircraft);

            Route route = routeRepository.getById(Long.parseLong(parts[2]));
            flight.setRoute(route);

            HashMap<SeatType, Integer> seatOccupiedMap = new HashMap<>();
            HashMap<SeatType, Double> priceMap = new HashMap<>();

            SeatType businessSeat = SeatType.valueOf(parts[3]);
            seatOccupiedMap.put(businessSeat, Integer.parseInt(parts[4]));
            priceMap.put(businessSeat, Double.parseDouble(parts[5]));

            SeatType economySeat = SeatType.valueOf(parts[6]);
            seatOccupiedMap.put(economySeat, Integer.parseInt(parts[7]));
            priceMap.put(economySeat, Double.parseDouble(parts[8]));

            flight.setSeatOccupiedMap(seatOccupiedMap);
            flight.setPriceMap(priceMap);

            flights.add(flight);
        }

        return flights;
    }

    @Override
    public List<String> dataToString(List<Flight> items) {
        List<String> data = new ArrayList<>();
        for (Flight c : items) {
            data.add(dataToString(c));
        }
        return data;
    }

    @Override
    public String dataToString(Flight flight) {
        return flight.getId() + ";" + flight.getAircraft().getId() + ";" + flight.getRoute().getId() + ";"
                + SeatType.BUSINESS + ";" + flight.getOccupiedSeatsBySeatType(SeatType.BUSINESS) + ";" + flight.getPriceBySeatType(SeatType.BUSINESS) + ";"
                + SeatType.ECONOMY + ";" + flight.getOccupiedSeatsBySeatType(SeatType.ECONOMY) + ";" + flight.getPriceBySeatType(SeatType.ECONOMY) + ";";
    }
}
