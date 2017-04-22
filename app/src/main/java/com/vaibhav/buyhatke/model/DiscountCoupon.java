package com.vaibhav.buyhatke.model;

/**
 * Created by vaibhav on 23/4/17.
 */

public class DiscountCoupon implements Comparable<DiscountCoupon> {

    String coupon;
    double discount;

    public DiscountCoupon(String coupon, double discount) {
        this.coupon = coupon;
        this.discount = discount;
    }


    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public int compareTo(DiscountCoupon discountCoupon) {
        boolean b = discountCoupon.getDiscount() > this.getDiscount();
        return b ? 1 : -1;
    }
}
