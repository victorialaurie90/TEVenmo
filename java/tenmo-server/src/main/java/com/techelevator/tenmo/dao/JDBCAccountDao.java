package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//Life is pain
// -_-
@Component
public
class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public BigDecimal getBalance(int userId) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
       BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
        return balance;
    }

    @Override
    //TODO Remove logged-in user from list
    public List<Account> listAllUsers() {
        List<Account> account = new ArrayList<>();
        String sql = "SELECT user_id, username FROM users;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()) {
            account.add(mapRowToAccount(results));
        }
        return account;
    }

    //TODO: Insufficient funds exception, might need try/catch, make sure to test
    @Override
    public void subtractFromBalance(int userId, BigDecimal amountToSubtract) {
        Account account = new Account();
        BigDecimal newBalance = account.getBalance().subtract(amountToSubtract);
        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
        jdbcTemplate.update(sql, userId, newBalance);
    }

    @Override
    public void addToBalance(int userId, BigDecimal amountToAdd) {
        Account account = new Account();
        BigDecimal newBalance = account.getBalance().add(amountToAdd);
        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
        jdbcTemplate.update(sql, userId, newBalance);
    }

    @Override
    public String findUsernameByAccountId(int accountId) {
        String username = "";
        String sql = "SELECT username FROM users " +
                    "INNER JOIN accounts ON users.user_id = accounts.user_id " +
                    "WHERE accounts.account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        if (result.next()) {
            username = result.getString("username");
        }
        return username;
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
