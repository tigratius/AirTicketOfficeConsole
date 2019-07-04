package main.java.com.tigratius.airticketofficeconsole.view;

import main.java.com.tigratius.airticketofficeconsole.controller.AccountController;
import main.java.com.tigratius.airticketofficeconsole.model.Message;

import java.util.Scanner;

class LoginView {

    private AccountController accountController;
    private TicketView ticketView;
    private FlightView flightView;

    private Scanner sc;

    private final String headMessage = "*Приложение по продаже билетов*";
    private final String authorizationMessage = "Авторизация кассира";
    private final String inputNameMessage = "Введите имя:";
    private final String inputPasswordMessage = "Введите пароль:";

    private final String menuMessage = "Выберете действие:\n" +
            "1. Поиск рейсов\n" +
            "2. Поиск билетов\n" +
            "3. Покупка билета\n" +
            "4. Возврат билета\n" +
            "5. Все рейсы\n" +
            "6. Все билеты\n" +
            "7. Выход";

    LoginView(AccountController accountController, TicketView ticketView, FlightView flightView, Scanner sc) {
        this.accountController = accountController;
        this.ticketView = ticketView;
        this.flightView = flightView;
        this.sc = sc;
    }

    void authorize()
    {
        System.out.println(Message.LINE.getMessage());
        System.out.println(headMessage);
        boolean isExit = false;
        do {
            System.out.println(Message.LINE.getMessage());
            System.out.println(authorizationMessage);

            System.out.println(inputNameMessage);
            String name = sc.next();
            System.out.println(inputPasswordMessage);
            String password = sc.next();

            if (accountController.logIn(name, password)) {
                isExit = true;
                System.out.println(Message.SUCCESSFUL_OPERATION.getMessage());
                showMainMenu();
            } else {
                System.out.println(Message.ERROR_OPERATION.getMessage());
            }

        } while (!isExit);
        sc.close();
    }

    private void showMainMenu()  {
        boolean isExit = false;
        do {
            System.out.println(Message.LINE.getMessage());
            System.out.println(menuMessage);
            System.out.println(Message.LINE.getMessage());
            String response = sc.next();
            switch (response) {
                case "1":
                    flightView.findFlights();
                    break;
                case "2":
                    ticketView.findTickets();
                    break;
                case "3":
                    ticketView.buyTicket();
                    break;
                case "4":
                    ticketView.returnTicket();
                    break;
                case "5":
                    flightView.showFlights();
                    break;
                case "6":
                    ticketView.showTickets();
                    break;
                case "7":
                    isExit = true;
                    break;
                default:
                    System.out.println(Message.ERROR_INPUT.getMessage());
                    break;
            }
        } while (!isExit);
    }
}
