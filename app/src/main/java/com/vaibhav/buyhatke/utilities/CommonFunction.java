package com.vaibhav.buyhatke.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.vaibhav.buyhatke.R;

/**
 * Created by vaibhav on 22/4/17.
 */
public class CommonFunction {


    private static CommonFunction ourInstance;
    ProgressDialog progressDialog;
    Context context;

    public CommonFunction(Context context) {
        this.context = context;
    }

    public static void destroy() {
        ourInstance = null;
    }

    public static CommonFunction getInstance(Context context) {

        if (ourInstance == null) {
            ourInstance = new CommonFunction(context);
        }
        return ourInstance;
    }

    public void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(context.getString(R.string.wait));
        }
        progressDialog.show();

    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showText(int message) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
