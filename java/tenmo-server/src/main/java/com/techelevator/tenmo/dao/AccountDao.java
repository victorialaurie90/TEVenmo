package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface AccountDao {
    BigDecimal getBalance(int userId);

    List<Account> listAllUsers();

    String subtractFromBalance(BigDecimal amount, int userId);

    String addToBalance(BigDecimal amount, int userId);

    String findUsernameByAccountId(int accountId);

    BigDecimal getBalanceByAccountId(int accountId);

    int getAccountIdByUserId(int userId);
}
