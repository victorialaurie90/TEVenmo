package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferService {

    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    public static String AUTH_TOKEN = "";
    private AuthenticatedUser currentUser;

    public TransferService(String url, AuthenticatedUser currentUser) {
        this.BASE_URL = "http://localhost:8080";
        this.currentUser = currentUser;
    }

public List<Transfer> getAllTransfers(int userId) {
        List<Transfer> transfer = new ArrayList<>();
        transfer = restTemplate.exchange(BASE_URL + "/accounts/" + userId + "/transfer", HttpMethod.GET, makeAuthEntity(), List.class).getBody();
    return transfer;
}

public Transfer getTransferById (int transferId) {
        Transfer transfer = new Transfer();
        transfer = restTemplate.exchange(BASE_URL + "/accounts/transfer/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
        return transfer;
    }

    //TODO: WTF do we do with sendTransfer??

    public Transfer insertSuccessfulTransfer(Transfer transfer) {
        transfer = restTemplate.postForObject(BASE_URL + "/accounts/transfer/" + transfer.getTransferId(), makeAuthEntity(), Transfer.class);
        return transfer;
    }

    public Transfer insertFailedTransfer(Transfer transfer) {
        transfer = restTemplate.postForObject(BASE_URL + "/accounts/transfer/" + transfer.getTransferId(), makeAuthEntity(), Transfer.class);
        return transfer;
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
