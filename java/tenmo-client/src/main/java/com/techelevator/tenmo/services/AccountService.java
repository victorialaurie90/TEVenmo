package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    public static String AUTH_TOKEN = "";
    private AuthenticatedUser currentUser;

    public AccountService(String url, AuthenticatedUser currentUser) {
        API_BASE_URL = "http://localhost:8080";
        this.currentUser = currentUser;
    }

    //TODO: RestClientResponse Exceptions on all -> wrap in try/catch

    public int getAccountByUserId(int userId, AuthenticatedUser currentUser){
        Integer accountId = 0;
        try {
            accountId = restTemplate.exchange(API_BASE_URL + "/account" + userId, HttpMethod.GET, makeAuthEntity(currentUser), Integer.class).getBody();
        } catch (Exception ex){
            System.out.println("Something isn't right.... maybe your user ID is invalid?");
        }
        return accountId;
    }

    public BigDecimal getBalance(AuthenticatedUser currentUser) {
        BigDecimal balance;
        balance = restTemplate.exchange(API_BASE_URL + "/account/" + currentUser.getUser().getId() + "/balance", HttpMethod.GET, makeAuthEntity(currentUser), BigDecimal.class).getBody();
        System.out.println("Your current balance is " + balance + ".");
        return balance;
    }

    public User[] listAllUsers(AuthenticatedUser currentUser) {
        User[] users;
        users = restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(currentUser), User[].class).getBody();
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.println("ID             Name");
        System.out.println("-------------------------------------------");

        for (User i : users) {
            //TODO: Figure out how to not show current user
            if (i.getId() != currentUser.getUser().getId()) {
                System.out.println(i.getId() + "          " + i.getUsername());
            }
        }
        return users;
    }

    //public Integer getAccountByUserId(int userId){}

    private HttpEntity makeAuthEntity(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
