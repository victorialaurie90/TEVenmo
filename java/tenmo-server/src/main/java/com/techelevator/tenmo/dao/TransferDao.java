package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import javax.validation.Valid;
import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransfers(int userId);

    Transfer getTransferById(int transferId);

    //TODO: Throws exception for insufficient funds
    Transfer sendTransfer(Transfer transfer);

    Transfer insertSuccessfulTransfer();

    Transfer insertFailedTransfer();
}
