package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JDBCTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(){
        this.transferDao = new JDBCTransferDao();
    }

    //TODO FIGURE OUT THE PATHING!?
    
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(@PathVariable int id) {
        List<Transfer> transfersList = transferDao.getAllTransfers(id);
        return transfersList;
    }

    @RequestMapping(value = "/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id) {
        Transfer transfer = transferDao.getTransferById(id);
        return transfer;
    }

    //TODO sendTransfer??

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public Transfer insertSuccessfulTransfer(@RequestBody Transfer newTransfer) {
        return transferDao.insertSuccessfulTransfer();
    }




}
