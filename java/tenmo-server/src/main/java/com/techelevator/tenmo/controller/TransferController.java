package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JDBCTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.instrument.classloading.jboss.JBossLoadTimeWeaver;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao){
        this.transferDao = new JDBCTransferDao();
    }

    //TODO FIGURE OUT THE PATHING!?
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(@Valid @PathVariable int id) {
        List<Transfer> transfersList = transferDao.getAllTransfers(id);
        return transfersList;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Transfer getTransferById(@Valid @PathVariable int id) {
        Transfer transfer = transferDao.getTransferById(id);
        return transfer;
    }


    //TODO sendTransfer??

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Transfer insertSuccessfulTransfer(@RequestBody Transfer newTransfer) {
        return transferDao.insertSuccessfulTransfer(newTransfer);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Transfer insertFailedTransfer(@RequestBody Transfer newTransfer) {
        return transferDao.insertFailedTransfer(newTransfer);
    }

}
