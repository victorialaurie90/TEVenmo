package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    public static String AUTH_TOKEN = "";
    private AuthenticatedUser currentUser;

    public AccountService(String url, AuthenticatedUser currentUser) {
        this.BASE_URL = "http://localhost:8080";
        this.currentUser = currentUser;
    }

    //TODO: RestClientResponse Exceptions on all -> wrap in try/catch

    public BigDecimal getBalance(int userId) {
        BigDecimal balance = new BigDecimal(0.00);
        balance = restTemplate.exchange(BASE_URL + "/accounts/" + userId + "/balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class).getBody();
        return balance;
    }

    public List<User> listAllUsers() {
        List<User> users = new ArrayList<>();
        users = restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(), List.class).getBody();
        return users;
    }

    //TODO: What to do with subtractFromBalance and addToBalance

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

}
