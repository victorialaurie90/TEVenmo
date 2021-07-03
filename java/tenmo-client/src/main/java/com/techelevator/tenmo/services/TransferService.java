package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    public static String AUTH_TOKEN = "";
    private AuthenticatedUser currentUser;

    public TransferService(String url, AuthenticatedUser currentUser) {
        this.API_BASE_URL = "http://localhost:8080";
        this.currentUser = currentUser;
    }

    public Transfer[] getAllTransfers(AuthenticatedUser currentUser) {
        Transfer[] transfers;
        transfers = restTemplate.exchange(API_BASE_URL + "/transfers", HttpMethod.GET, makeAuthEntity(currentUser), Transfer[].class).getBody();
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID             From/To             Amount");
        System.out.println("-------------------------------------------");
        for (Transfer i : transfers) {
            if (i.getAccountFromName() == currentUser.getUser().getUsername()) {
                System.out.println(i.getTransferId() + "          To: " + i.getAccountTo() + "            $ " + i.getAmount());
            } else if (i.getAccountToName() == currentUser.getUser().getUsername()) {
                System.out.println(i.getTransferId() + "         From: " + i.getAccountFrom() + "        $ " + i.getAmount());
            }
        }
        return transfers;
    }

    public Transfer getTransferById(int transferId) {
        Transfer transfer = new Transfer();
        transfer = restTemplate.exchange(API_BASE_URL + "/transfers/" + transferId, HttpMethod.GET, makeAuthEntity(currentUser), Transfer.class).getBody();
        return transfer;
    }

    public Transfer sendTransfer(Transfer transfer, AuthenticatedUser currentUser) {
        try {
            Transfer currentTransfer = restTemplate.exchange(API_BASE_URL + "/transfers", HttpMethod.POST, makeTransferEntity(transfer, currentUser), Transfer.class).getBody();
            System.out.println("Transfer was successful.");
            return currentTransfer;
        } catch (Exception e) {
            System.out.println("This happened: " + e.getMessage() + " and " + e.getCause());
            return null;
        }
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
