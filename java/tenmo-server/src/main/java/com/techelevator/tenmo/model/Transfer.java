package com.techelevator.tenmo.model;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class Transfer {

    @NotBlank(message = "Transfer ID cannot be blank.")
    private int transferId;

    @NotBlank(message = "Transfer Type ID cannot be blank.")
    private int transferTypeId;

    @NotBlank(message = "Transfer Status ID cannot be blank.")
    private int transferStatusId;

    @NotBlank(message = "From Account field cannot be blank.")
    private int accountFrom;

    @NotBlank(message = "To Account field cannot be blank.")
    private int accountTo;

    @NotBlank(message = "From Account name cannot be blank.")
    private String accountFromName;

    @NotBlank(message = "To Account name cannot be blank.")
    private String accountToName;

    @DecimalMin(value = "0.01", message = "Transfer amount must be greater than 0.")
    private BigDecimal amount;

    public Transfer(int transferId, int transferTypeId, int transferStatusId, int accountFrom, int accountTo, String accountFromName, String accountToName, BigDecimal amount){
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountFrom = accountTo;
        this.accountFromName = accountFromName;
        this.accountToName = accountToName;
        this.amount = amount;

    }

    public Transfer() {

    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountFromName() {
        return accountFromName;
    }

    public void setAccountFromName(String accountFromName) {
        this.accountFromName = accountFromName;
    }

    public String getAccountToName() {
        return accountToName;
    }

    public void setAccountToName(String accountToName) {
        this.accountToName = accountToName;
    }
}
