package main.java.com.tigratius.airticketofficeconsole.controller;

import main.java.com.tigratius.airticketofficeconsole.service.AccountService;

public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public boolean logIn(String name, String password)
    {
        return accountService.logIn(name, password);
    }
}
