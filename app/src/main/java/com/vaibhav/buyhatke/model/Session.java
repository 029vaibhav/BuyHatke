package com.vaibhav.buyhatke.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vaibhav on 22/4/17.
 */


public class Session {

    @JsonProperty("USER_TOKEN")
    String userToken;
    @JsonProperty("CART:totalQuantity")
    int quantity;
    @JsonProperty("CART:totalAmountCart")
    double amount;
    @JsonProperty("isLoggedIn")
    boolean loggedIn;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
