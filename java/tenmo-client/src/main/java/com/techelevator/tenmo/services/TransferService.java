package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.apiguardian.api.API;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class TransferService {

    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    // public static String AUTH_TOKEN = ""; -> Do we need this?
    private AuthenticatedUser currentUser;
    AccountService accountService;

    public TransferService(String API_BASE_URL, AccountService accountService) {
        this.API_BASE_URL = API_BASE_URL;
        this.accountService = accountService;
    }

    public void getAllTransfers(AuthenticatedUser currentUser, AccountService accountService) throws NullPointerException {
        Transfer[] transfers;

        int accountId = accountService.getAccountIdByUserId(currentUser.getUser().getId(), currentUser);
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID             From/To             Amount");
        System.out.println("-------------------------------------------");

        try {
            transfers = restTemplate.exchange(API_BASE_URL + "/transfers", HttpMethod.GET, makeAuthEntity(currentUser), Transfer[].class).getBody();
            for (Transfer i : transfers) {
                if (i.getAccountFrom() == accountId) {
                    System.out.println(i.getTransferId() + "          To: " + i.getAccountTo() + "            $ " + i.getAmount());
                } else if (i.getAccountTo() == accountId) {
                    System.out.println(i.getTransferId() + "          From: " + i.getAccountFrom() + "        $ " + i.getAmount());
                }
            }
        }catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    public Transfer getTransferById(int transferId) {
        Transfer transfer = new Transfer();
        transfer = restTemplate.exchange(API_BASE_URL + "/transfers/" + transferId, HttpMethod.GET, makeAuthEntity(currentUser), Transfer.class).getBody();
        return transfer;
    }

    public Transfer sendTransfer(Transfer transfer, AuthenticatedUser currentUser) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("-------------------------------------------\r\n" +
                "Enter ID of user you are sending to (0 to cancel): ");
        transfer.setAccountTo(Integer.parseInt(scanner.nextLine()));
        transfer.setAccountFrom(currentUser.getUser().getId());
        if (transfer.getAccountTo() != 0) {
            System.out.print("Enter amount: ");
        } else {

            try {
                Transfer currentTransfer = restTemplate.exchange(API_BASE_URL + "/transfers", HttpMethod.POST, makeTransferEntity(transfer, currentUser), Transfer.class).getBody();
                System.out.println("Transfer was successful.");
                return currentTransfer;
            } catch (Exception e) {
                System.out.println("This happened: " + e.getMessage() + " and " + e.getCause());
            }
        }
        return transfer;
    }

    private HttpEntity makeAuthEntity(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer, AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }
}
