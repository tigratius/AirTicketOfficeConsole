package main.java.com.tigratius.airticketofficeconsole.view;

import main.java.com.tigratius.airticketofficeconsole.controller.FlightController;
import main.java.com.tigratius.airticketofficeconsole.model.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class FlightView {

    private FlightController flightController;

    private final String headerFlights = "Доступные рейсы\n" +
            "ID; AircraftName; DepartureDate; DepartureCity; ArrivalDate; ArrivalCity; BusinessFreeSeats; BusinessPrice; EconomyFreeSeats; EconomyPrice";

    private final String findFlightsMessage = "Поиск рейсов";

    private Scanner sc;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat dateFormatSimple = new SimpleDateFormat("yyyy-MM-dd");

    FlightView(FlightController flightController, Scanner sc) {
        this.flightController = flightController;
        this.sc = sc;
    }

    void findFlights(){
        System.out.println(Message.LINE.getMessage());
        System.out.println(findFlightsMessage);
        System.out.println(Message.INPUT_DATE.getMessage());
        sc.nextLine();
        String date = sc.nextLine();

        System.out.println(Message.INPUT_DEPARTURE.getMessage());
        String departure = sc.nextLine();

        System.out.println(Message.INPUT_ARRIVAL.getMessage());
        String arrival = sc.nextLine();

        System.out.println(Message.LINE.getMessage());

        List<Flight> flights;
        try
        {
            Date parsedDate = null;
            if (!date.isEmpty()) {
                parsedDate = dateFormatSimple.parse(date);
            }
            flights = flightController.findFlights(parsedDate, departure, arrival);
            printFlights(flights);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());

        }

        System.out.println(Message.LINE.getMessage());
    }

    void showFlights() {

        System.out.println(Message.LINE.getMessage());
        List<Flight> flights;

        try {
            flights = flightController.getFlights();
            printFlights(flights);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }

    private void printFlights(List<Flight> flights)
    {
        if (flights.isEmpty())
        {
            System.out.println(Message.EMPTY_LIST.getMessage());
            return;
        }
        System.out.println(headerFlights);

        flights.sort(Comparator.comparing(BaseEntity::getId));
        for (Flight f : flights
        ) {
            Aircraft aircraft = f.getAircraft();
            Route route = f.getRoute();

            System.out.println(f.getId() + "; " + aircraft.getName() + "; " + dateFormat.format(route.getDepartureDate()) + "; " + route.getDeparture().getName() + "; "
                    + dateFormat.format(route.getArrivalDate()) + "; " + route.getArrival().getName() + "; "
                    + f.getFreeSeatsBySeatType(SeatType.BUSINESS) + "; " + f.getPriceBySeatType(SeatType.BUSINESS) + "; "
                    + f.getFreeSeatsBySeatType(SeatType.ECONOMY)+ "; " + f.getPriceBySeatType(SeatType.ECONOMY));
        }
    }
}
