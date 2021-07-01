package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")

public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/account/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id) {
        BigDecimal balance = accountDao.getBalance(id);
        return balance;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> listUsers() {
        List<User> users = userDao.findAll();
        return users;

        //In case we need to go back to using principal
        /*@RequestMapping(path = "/balance/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        BigDecimal balance = accountDao.getBalance(userId);
        return balance;
    }*/
    }
}
