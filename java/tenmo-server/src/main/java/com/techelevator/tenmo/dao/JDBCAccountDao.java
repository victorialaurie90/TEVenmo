package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;
    //Ask Rich about this...
    // private UserDao userDao;

    public JDBCAccountDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Account getBalance(int userId) {
        Account account = new Account();
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    //TODO Remove logged-in user from list
    public List<Account> listAllUsers() {
        List<Account> account = new ArrayList<>();
        String sql = "SELECT user_id, username FROM users;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            if (results.next()){
                account.add(mapRowToAccount(results));
            }
        return account;
    }

    @Override
    public void updateBalance(int userId, BigDecimal balance) {
        Account account = new Account();
        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
        jdbcTemplate.update(sql, account.getUserId(), account.getBalance());
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
