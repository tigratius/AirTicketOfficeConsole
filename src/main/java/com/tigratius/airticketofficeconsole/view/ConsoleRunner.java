package main.java.com.tigratius.airticketofficeconsole.view;

import main.java.com.tigratius.airticketofficeconsole.controller.AccountController;
import main.java.com.tigratius.airticketofficeconsole.controller.FlightController;
import main.java.com.tigratius.airticketofficeconsole.controller.TicketController;
import main.java.com.tigratius.airticketofficeconsole.model.User;
import main.java.com.tigratius.airticketofficeconsole.repository.*;
import main.java.com.tigratius.airticketofficeconsole.repository.io.*;
import main.java.com.tigratius.airticketofficeconsole.service.AccountService;
import main.java.com.tigratius.airticketofficeconsole.service.FlightService;
import main.java.com.tigratius.airticketofficeconsole.service.TicketService;

import java.util.Scanner;

public class ConsoleRunner {

    private LoginView loginView;
    private final String damagedDataMessage = "Данные повреждены!";
    private Scanner sc = new Scanner(System.in);

    public ConsoleRunner(){
        try {
            //user
            User user = new User("user", "123456");

            //create repo
            CityRepository cityRepository = new JavaIOCityRepositoryImpl();
            AircraftRepository aircraftRepository = new JavaIOAirCraftRepositoryImpl();
            RouteRepository routeRepository = new JavaIORouteRepositoryImpl(cityRepository);
            FlightRepository flightRepository = new JavaIOFlightRepositoryImpl(aircraftRepository, routeRepository);
            TicketRepository ticketRepository = new JavaIOTicketRepositoryImpl(flightRepository);

            //create services
            FlightService flightService = new FlightService(flightRepository);
            TicketService ticketService = new TicketService(flightRepository, ticketRepository);
            AccountService accountService = new AccountService(user);

            //create controllers
            AccountController accountController = new AccountController(accountService);
            TicketController ticketController = new TicketController(ticketService);
            FlightController flightController = new FlightController(flightService);

            //create views
            TicketView ticketView = new TicketView(ticketController, sc);
            FlightView flightView = new FlightView(flightController, sc);
            loginView = new LoginView(accountController, ticketView, flightView, sc);
        }
        catch (Exception ex)
        {
            System.out.println(damagedDataMessage);
        }
    }

    public void authorize() {
        loginView.authorize();
    }
}
