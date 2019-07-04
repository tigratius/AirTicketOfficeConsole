package main.java.com.tigratius.airticketofficeconsole.service;

import main.java.com.tigratius.airticketofficeconsole.model.User;

public class AccountService {

    private User user;

    public AccountService(User user) {
        this.user = user;
    }

    public boolean logIn(String name, String password)
    {
        return name.equals(user.getName()) && password.equals(user.getPassword());
    }
}
