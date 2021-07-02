package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;
    private UserDao userDao;

    public TransferController(TransferDao transferDao, UserDao userDao){
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@Valid @PathVariable int id) {
        Transfer transfer = transferDao.getTransferById(id);
        return transfer;
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public String sendTransfer(@RequestBody Transfer transfer) {
        Transfer newTransfer = new Transfer(transfer.getTransferId(),transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        transferDao.sendTransfer(newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());
        return "Yay, transfer worked!";
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        List<Transfer> transfersList = transferDao.getAllTransfers(userId);
        return transfersList;
    }

    /*@RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(int id) {
        List<Transfer> transfersList = transferDao.getAllTransfers(id);
        return transfersList;
    }*/

    /* Don't think we need this in controller, as no pathway should be needed?
    @RequestMapping(value = "/transfers", method = RequestMethod.POST)
    public Transfer insertTransfer(@RequestBody Transfer newTransfer) {
        return transferDao.insertSuccessfulTransfer(newTransfer);
    }*/
}
