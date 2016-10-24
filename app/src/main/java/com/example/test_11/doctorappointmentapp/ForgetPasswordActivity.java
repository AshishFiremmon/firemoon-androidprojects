package com.example.test_11.doctorappointmentapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Util.AppUrl;
import Util.DialogInterfacecustom;
import Util.Validation;
import web.CheckInternetConnectio;
import web.IResponse;
import web.Web;

public class ForgetPasswordActivity extends Activity implements IResponse {
EditText emailEt;
    ProgressDialog mProgressDialog;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        handler=new Handler();
        emailEt=(EditText)findViewById(R.id.emailEt);
        findViewById(R.id.sendBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.isEmailAddress(emailEt, true)) {

                    CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                            ForgetPasswordActivity.this);

                    if(_checkInternetConnection.checkInterntConnection()) {
                        resetPassword();

                    }else
                    {

                    }
                } else {
                    emailEt.setError("Enter valid Emai id");
                }
            }
        });


    }

    @Override
    public void onComplete(final String result, int i) {
        mProgressDialog.cancel();
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                    if (obj.has("error")) {
                        DialogInterfacecustom.loginResponceDialog(ForgetPasswordActivity.this, obj.getString("message").toString(), "");
                        return;
                    } else {
                        DialogInterfacecustom.loginResponceDialog(ForgetPasswordActivity.this, obj.getString("message").toString(), "");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        });
    }

    public void resetPassword() {
        mProgressDialog = ProgressDialog.show(ForgetPasswordActivity.this, null,
                "Please Wait....", true);
        mProgressDialog.setCancelable(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("email",emailEt.getText().toString()));
                new Web().requestPostStringData(AppUrl.resetpasswordUrl, data, ForgetPasswordActivity.this, 100);

            }
        }).start();
    }
}
