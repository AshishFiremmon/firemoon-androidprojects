package com.example.test_11.doctorappointmentapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class SignIn extends Activity implements IResponse {
Button signBtn,registerBtn;
    TextView forgetpassBtn;
    EditText emailEt,passwordEt;
    ProgressDialog mProgressDialog;
    private Handler handler;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
       signBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(Validation.isEmailAddress(emailEt,true)) {
                   if(!passwordEt.getText().toString().equalsIgnoreCase("")) {
                       CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                               SignIn.this);

                       if(_checkInternetConnection.checkInterntConnection())
                       {
                           login();
                           mProgressDialog = ProgressDialog.show(SignIn.this, null, "Please Wait....", true);
                           mProgressDialog.setCancelable(true);

                       }
                       else {
                           Toast.makeText(SignIn.this, "Check Internet Connection",
                                   Toast.LENGTH_SHORT).show();
                       }
                   }else
                   {
                       passwordEt.setError("Enter password");
                   }
               }else
               {
                   emailEt.setError("Enter valid Email id");
               }

           }
       });
       registerBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(SignIn.this, RegisterActivity.class));
           }
       });
       forgetpassBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            startActivity(new Intent(SignIn.this, ForgetPasswordActivity.class));
           }
       });
    }

    void initView()
    {
      signBtn=(Button)findViewById(R.id.signin);
        registerBtn=(Button)findViewById(R.id.signup);
        forgetpassBtn=(TextView)findViewById(R.id.passwordTxt);
        emailEt=(EditText)findViewById(R.id.emailEt);
        passwordEt=(EditText)findViewById(R.id.passEt);
        handler = new Handler();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean yourLocked = sharedpreferences.getBoolean("LOGIN", false);
        Log.i("hello", yourLocked + "..."+sharedpreferences.getString("USER_ID",""));
        String userType = sharedpreferences.getString("USER_TYPE", "");
        if(yourLocked)
        {
            if(userType.equals("Doctor")) {
                if(DoctorPorileActivity.login)
                {
                    startActivity(new Intent(SignIn.this, BaseActivityForDoctor.class));
                    finish();
                }
                else {
                    Intent intent = new Intent(SignIn.this, BaseActivityForDoctor.class);
                    startActivity(intent);
                    finish();
                }
            }else
            {
                Intent intent = new Intent(SignIn.this, TabHigherVersionActivity.class);
                startActivity(intent);
                finish();
            }

        }

    }
    public void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("email", emailEt.getText().toString()));
                data.add(new BasicNameValuePair("password",  passwordEt.getText().toString()));
                new Web().requestPostStringData(AppUrl.loginUrl, data, SignIn.this, 100);



            }
        }).start();
    }
    @Override
    public void onComplete(final String result, int i) {
        // TODO Auto-generated method stub
        mProgressDialog.cancel();

        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    if (obj.getString("error").equals("false")) {
                        JSONObject jObj2=obj.getJSONObject("login_data");

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("LOGIN", true);
                        editor.putString("USER_ID", jObj2.getString("user_id"));
                        editor.putString("USER_TYPE", jObj2.getString("user_type"));

                        editor.commit();
                        Toast.makeText(SignIn.this, "Successfully Login....", Toast.LENGTH_LONG).show();
                        if(jObj2.getString("user_type").equals("Doctor")) {
                            Intent intent = new Intent(SignIn.this, BaseActivityForDoctor.class);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            Intent intent = new Intent(SignIn.this, TabHigherVersionActivity.class);
                            startActivity(intent);
                            finish();
                        }


                    } else {
                        DialogInterfacecustom.loginResponceDialog(SignIn.this, obj.getString("message").toString(), "");


                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }
}
