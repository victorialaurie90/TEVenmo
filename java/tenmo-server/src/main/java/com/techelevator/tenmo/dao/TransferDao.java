package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransfers(int userId);

    Transfer getTransferById(int transferId);

    Transfer insertTransfer(Transfer transfer, BigDecimal requestAmount);

}
