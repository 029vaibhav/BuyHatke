package com.vaibhav.buyhatke;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.vaibhav.buyhatke.customviews.ClickEvent;
import com.vaibhav.buyhatke.customviews.DragableFloatingButton;
import com.vaibhav.buyhatke.model.DiscountCoupon;
import com.vaibhav.buyhatke.model.Myntra;
import com.vaibhav.buyhatke.model.MyntraCouponRequest;
import com.vaibhav.buyhatke.model.Session;
import com.vaibhav.buyhatke.network.ApiClient;
import com.vaibhav.buyhatke.utilities.CommonFunction;
import com.vaibhav.buyhatke.utilities.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends AppCompatActivity implements ClickEvent {

    @Override
    protected void onDestroy() {
        CommonFunction.destroy();
        super.onDestroy();
    }

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.coupon_view)
    TextView couponView;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    Session session;
    boolean firstTime = true;
    String baseURL = "http://www.myntra.com";
    List<DiscountCoupon> discountCouponList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        couponView.setMovementMethod(new ScrollingMovementMethod());
        floatingActionButton.setOnTouchListener(new DragableFloatingButton(this));
        getToken();
        loadWebView();
        couponView.setOnClickListener(view -> couponView.setVisibility(View.GONE));


    }

    private void loadWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(baseURL);

    }

    private void getToken() {

        getCookies(baseURL);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable callable = new Callable() {
            @Override
            public Myntra call() throws Exception {
                return ApiClient.getInstance().getService().getSession(getCurrentCookie()).execute().body();
            }
        };
        Future submit = executorService.submit(callable);
        try {
            Myntra session = (Myntra) submit.get();
            this.session = session.getSession();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            interceptUrl(url);
        }
    };

    boolean checkUrl(String s) {

        return s.contains("checkout/cart");
    }

    private void interceptUrl(String url) {

        if (checkUrl(url)) {
            floatingActionButton.setVisibility(View.VISIBLE);


        } else {
            floatingActionButton.setVisibility(View.GONE);
        }

    }

    void getCookies(String url) {

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
    }

    String getCurrentCookie() {
        return CookieManager.getInstance().getCookie(baseURL);
    }


    @Override
    public void onClick() {

        setVisibility(View.VISIBLE);
        ApiClient.getInstance().getService().getCoupons("1").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String str = new String(response.body().bytes());
                        String[] split = str.split("~");
                        split[0] = "Myntranew1000";
                        applyCoupons(split);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void applyCoupons(String[] split) {

        final Pattern pattern = Pattern.compile("(You saved Rs. )(\\d+.?\\d+)");
        for (int i = 0; i < split.length - 1; i++) {
            final String couponCode = split[i];
            couponView.setText(couponCode);
            String body = callCouponApi(couponCode);
            processCouponResponse(couponCode, body, pattern);

        }
        if (discountCouponList.size() == 0) {
            couponView.setText(R.string.no_coupon);
        } else {
            Collections.sort(discountCouponList);
            couponView.setText("coupon applied " + discountCouponList.get(0).getCoupon() + " Rs" + discountCouponList.get(0).getDiscount() + " off");
            callCouponApi(discountCouponList.get(0).getCoupon());
            webView.reload();
        }
        setVisibility(View.GONE);


    }

    private void processCouponResponse(String couonCode, String body, Pattern pattern) {

        if (body.contains("You saved Rs")) {
            Matcher m = pattern.matcher(body);
            boolean b = m.find(0);
            if (b) {
                String group = m.group(2);
                discountCouponList.add(new DiscountCoupon(couonCode, Double.parseDouble(group)));
                couponView.setText(couponView.getText() + " - " + group);
            }
        }
    }

    private String callCouponApi(String coupon) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<ResponseBody> apply_coupon = executorService.submit(() -> {
            MyntraCouponRequest myntraCouponRequest = new MyntraCouponRequest(session.getUserToken(), coupon, Constants.APPLY_COUPON);
            return ApiClient.getInstance()
                    .getService().applyCoupon(getCurrentCookie(), "https://secure.myntra.com", myntraCouponRequest).execute().body();

        });

        String s1 = "";
        try {
            ResponseBody body = apply_coupon.get();
            s1 = new String(body.bytes());

        } catch (InterruptedException | ExecutionException e) {
            Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
        return s1;
    }

    private void setVisibility(int visibility) {
        if (visibility == 0) {
            CommonFunction.getInstance(this).showDialog();
            couponView.setVisibility(visibility);
        } else {
            CommonFunction.getInstance(this).dismissDialog();
        }
    }


}
