package com.vaibhav.buyhatke.model;

/**
 * Created by vaibhav on 22/4/17.
 */

public class MyntraCouponRequest {

    String token,coupon,operation;

    public MyntraCouponRequest(String token, String coupon, String operation) {
        this.token = token;
        this.coupon = coupon;
        this.operation = operation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
