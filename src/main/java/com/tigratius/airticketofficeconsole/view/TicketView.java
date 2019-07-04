package main.java.com.tigratius.airticketofficeconsole.view;

import main.java.com.tigratius.airticketofficeconsole.controller.TicketController;
import main.java.com.tigratius.airticketofficeconsole.model.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class TicketView {

    private TicketController ticketController;

    private final String printHeaderTickets = "Проданные билеты\n" +
            "ID; AircraftName; DepartureDate; DepartureCity; ArrivalDate; ArrivalCity; SeatType; FirstName; LastName; Price";

    private final String buyTicketMessage = "Покупка билета";
    private final String findTicketMessage = "Поиск билетов";
    private final String returnTicketMessage = "Возврат билета";
    private final String inputFlightIdMessage = "Введите ID номер рейса:";
    private final String inputTicketIdMessage = "Введите ID номер билета:";
    private final String inputSeatTypeMessage = "Выберете тип салона:\n" +
                                                "1. " + SeatType.BUSINESS + "\n" +
                                                "2. " + SeatType.ECONOMY ;

    private final String inputFirstNameMessage = "Введите имя:";
    private final String inputLastNameMessage = "Введите фамилию:";

    private Scanner sc;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat dateFormatSimple = new SimpleDateFormat("yyyy-MM-dd");

    TicketView(TicketController ticketController, Scanner sc) {
        this.ticketController = ticketController;
        this.sc = sc;
    }

    void buyTicket()
    {
        System.out.println(Message.LINE.getMessage());
        System.out.println(buyTicketMessage);

        System.out.println(inputFlightIdMessage);
        long flightId = sc.nextLong();

        System.out.println(inputSeatTypeMessage);
        int seatTypeId = sc.nextInt();
        SeatType seatType = SeatType.fromId(seatTypeId);

        System.out.println(inputFirstNameMessage);
        String firstName = sc.next();

        System.out.println(inputLastNameMessage);
        String lastName = sc.next();

        try {
            ticketController.buyTicket(flightId, seatType, firstName, lastName);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());

    }

    void returnTicket()
    {
        System.out.println(Message.LINE.getMessage());
        System.out.println(returnTicketMessage);

        System.out.println(inputTicketIdMessage);
        long ticketId = sc.nextLong();

        try {
            ticketController.returnTicket(ticketId);
            System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
        }

        System.out.println(Message.LINE.getMessage());
    }

    void findTickets(){
        System.out.println(Message.LINE.getMessage());
        System.out.println(findTicketMessage);

        System.out.println(Message.INPUT_DATE.getMessage());
        sc.nextLine();
        String date = sc.nextLine();

        System.out.println(Message.INPUT_DEPARTURE.getMessage());
        String departure = sc.nextLine();

        System.out.println(Message.INPUT_ARRIVAL.getMessage());
        String arrival = sc.nextLine();

        System.out.println(inputSeatTypeMessage);
        String seatTypeNumber = sc.nextLine();

        System.out.println(Message.LINE.getMessage());

        List<Ticket> tickets;
        try
        {
            Date parsedDate = null;
            SeatType seatType = null;

            if (!date.isEmpty()) {
                parsedDate = dateFormatSimple.parse(date);
            }

            if (!seatTypeNumber.isEmpty())
            {
                seatType = SeatType.fromId(Integer.parseInt(seatTypeNumber));
            }

            tickets= ticketController.findTicket(parsedDate, departure, arrival, seatType);
            printTickets(tickets);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());

        }

        System.out.println(Message.LINE.getMessage());
    }

    void showTickets() {

        System.out.println(Message.LINE.getMessage());
        List<Ticket> tickets;

        try {
            tickets = ticketController.getTickets();
            printTickets(tickets);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Message.ERROR_OPERATION.getMessage());
            return;
        }

        System.out.println(Message.LINE.getMessage());
    }

    private void printTickets(List<Ticket> tickets)
    {
        if (tickets.isEmpty())
        {
            System.out.println(Message.EMPTY_LIST.getMessage());
            return;
        }

        System.out.println(printHeaderTickets);
        tickets.sort(Comparator.comparing(BaseEntity::getId));
        for (Ticket t : tickets
        ) {
            Flight f = t.getFlight();
            Aircraft aircraft = f.getAircraft();
            Route route = f.getRoute();
            SeatType seatType = t.getSeatType();

            System.out.println(t.getId() + "; " + aircraft.getName() + "; " + dateFormat.format(route.getDepartureDate()) + "; " + route.getDeparture().getName() + "; "
                    + dateFormat.format(route.getArrivalDate()) + "; " + route.getArrival().getName() + "; " + seatType + "; " + t.getFirstName() + "; " + t.getLastName() + "; "
                    + f.getPriceBySeatType(seatType));

        }
    }
}
