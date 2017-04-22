package com.vaibhav.buyhatke.network;

import com.vaibhav.buyhatke.model.Myntra;
import com.vaibhav.buyhatke.model.MyntraCouponRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by vaibhav on 20/12/16.
 */
public interface ApiInterface {

    @GET("PickCoupon/FreshCoupon/getCoupons.php")
    Call<ResponseBody> getCoupons(@Query("pos") String pos);

    @POST("http://www.myntra.com/checkout/cart")
    Call<ResponseBody> applyCoupon(@Header("cookie") String cookie, @Header("x-requested-with") String xmlHeader, @Header("Origin") String origin, @Header("Host") String host, @Header("Referer") String referer, @Body MyntraCouponRequest myntraCouponRequest);

    @POST("https://secure.myntra.com/checkout/cart")
    Call<ResponseBody> applyCoupon(@Header("cookie") String cookie, @Header("Origin") String origin, @Body MyntraCouponRequest myntraCouponRequest);


    @POST("http://www.myntra.com/beacon/user-data")
    Call<Myntra> getSession(@Header("cookie") String cookie);


}