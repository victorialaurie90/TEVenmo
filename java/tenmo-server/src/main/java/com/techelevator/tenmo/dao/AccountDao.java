package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    Account getBalance(int userId);

    List<Account> listAllUsers();

    void updateBalance(int userId, BigDecimal balance);

}
