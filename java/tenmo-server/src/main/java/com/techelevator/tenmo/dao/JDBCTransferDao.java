package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JDBCTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JDBCTransferDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);


    }

    @Override
    public List<Transfer> getAllTransfers(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT trans.transfer_id, type.transfer_type_desc, users.username, trans.amount FROM transfers AS trans " +
                "INNER JOIN transfer_types as type ON trans.transfer_type_id = type.transfer_type_id " +
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
    public Transfer getTransferById(int transferId) {
        return null;
    }

    @Override
    public Transfer sendTransfer(Transfer transfer) {
        return null;
    }

    @Override
    public Transfer insertTransfer(Transfer transfer) {
        return null;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setAccountFrom(rowSet.getInt("account_from"));
        transfer.setAccountTo(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }
}
