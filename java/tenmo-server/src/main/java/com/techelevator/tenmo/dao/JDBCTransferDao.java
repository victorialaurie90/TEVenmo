package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//Life is still pain
// -_-
@Component
public
class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean sendTransfer(int accountFrom, int accountTo, BigDecimal amount) {
        if (amount.compareTo(accountDao.getBalance(accountFrom)) == -1 && amount.compareTo(new BigDecimal(0)) == 1) {
            String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (2, 2, ?, ?, ?);";
            try {
                jdbcTemplate.update(sql, accountFrom, accountTo, amount);
                accountDao.subtractFromBalance(accountFrom, amount);
                accountDao.addToBalance(accountTo, amount);
            } catch (Exception e) {
                System.out.println("Message " +e.getMessage() + " and also " + e.getCause());
                return false;
            }
            return true;
        } else {
            System.out.println("You don't have enough funds to complete the transaction");
            return false;
        }
    }

    @Override
    public List<Transfer> getAllTransfers(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT trans.transfer_id, trans.transfer_type_id, trans.transfer_status_id, trans.account_from, trans.account_to, users.username, users.username, trans.amount FROM transfers AS trans " +
                "INNER JOIN accounts ON trans.account_to = accounts.account_id " +
                "INNER JOIN users ON accounts.user_id = users.user_id " +
                "WHERE users.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    @Override
    public int findAccountIdByAccountFrom(int accountFrom) {
        int accountId = 0;
        String sql = "SELECT account_id FROM accounts " +
                "INNER JOIN transfers ON accounts.account_id = transfers.account_from " +
                "WHERE transfers.account_from = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountFrom);
        if (result.next()) {
            accountId = result.getInt("account_id");
        }
        return accountId;
    }

    @Override
    public int findAccountIdByAccountTo(int accountTo) {
        int accountId = 0;
        String sql = "SELECT account_id FROM accounts " +
                "INNER JOIN transfers ON accounts.account_id = transfers.account_to " +
                "WHERE transfers.account_to = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountTo);
        if (result.next()) {
            accountId = result.getInt("account_id");
        }
        return accountId;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = new Transfer();
        String sql = "SELECT trans.transfer_id, " +

                "(SELECT username FROM users " +
                "INNER JOIN accounts ON accounts.user_id = users.user_id " +
                "INNER JOIN transfers ON accounts.account_id = transfers.account_from " +
                "WHERE accounts.account_id = transfers.account_from), " +
                "(SELECT username FROM users " +
                "INNER JOIN accounts ON accounts.user_id = users.user_id " +
                "INNER JOIN transfers ON accounts.account_id = transfers.account_to " +
                "WHERE accounts.account_id = transfers.account_to), " +

                "type.transfer_type_desc, status.transfer_status_desc, trans.amount FROM transfers AS trans " +

                "INNER JOIN transfer_types as type ON trans.transfer_type_id = type.transfer_type_id " +
                "INNER JOIN transfer_statuses as status ON trans.transfer_status_id = status.transfer_status_id " +
                "INNER JOIN accounts ON trans.account_to = accounts.account_id " +
                "INNER JOIN users ON accounts.user_id = users.user_id " +
                "WHERE trans.transfer_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    //TODO Handle success and failure messages
    @Override
    public Transfer insertTransfer(Transfer transfer, BigDecimal requestAmount) {
        Account account = new Account();
        if (requestAmount.compareTo(account.getBalance()) >= 0) {
            String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (2, 2, ?, ?, ?);";
            jdbcTemplate.update(sql, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                    transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
            return transfer;

        } else {
            String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (2, 3, ?, ?, 0);";
            jdbcTemplate.update(sql, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                    transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        }
        return transfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setAccountFrom(rowSet.getInt("account_from"));
        transfer.setAccountTo(rowSet.getInt("account_to"));
        //transfer.setAccountFromName(accountDao.findUsernameByAccountId(rowSet.getInt("account_from")));
        //transfer.setAccountToName(accountDao.findUsernameByAccountId(rowSet.getInt("account_to")));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }
}
