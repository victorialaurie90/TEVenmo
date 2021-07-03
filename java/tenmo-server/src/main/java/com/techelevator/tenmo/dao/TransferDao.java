package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransfers(int userId);

    Transfer getTransferById(int transferId);

    int findAccountIdByAccountFrom(int accountFrom);

    int findAccountIdByAccountTo(int accountTo);

    String sendTransfer(int userFrom, int userTo, BigDecimal amount);
}
