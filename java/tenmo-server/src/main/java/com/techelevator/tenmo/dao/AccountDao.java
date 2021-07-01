package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    BigDecimal getBalance(int userId);

    List<Account> listAllUsers();

    void subtractFromBalance(int userId, BigDecimal amountToSubtract);

    void addToBalance(int userId, BigDecimal amountToAdd);

}
