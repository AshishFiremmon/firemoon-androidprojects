package com.example.test_11.doctorappointmentapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class RegisterActivity extends Activity implements IResponse {
    Button alreadyRegisterBtn,signupBtn;
    ProgressDialog mProgressDialog;
    private Handler handler;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

EditText emailEt,passswordEt,confirmPassEt;
    RadioButton radiodoctor,radiopatient;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
      initView();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Validation.isEmailAddress(emailEt, true)) {
                    if(!passswordEt.getText().toString().equalsIgnoreCase("")) {
                        if(!confirmPassEt.getText().toString().equalsIgnoreCase(""))
                        {
                            if(confirmPassEt.getText().toString().equalsIgnoreCase(passswordEt.getText().toString()))
                            {
                                CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                                        RegisterActivity.this);

                                if(_checkInternetConnection.checkInterntConnection())
                                {
//                                    new RegisterTask().execute();
                                    registration();
                                    mProgressDialog = ProgressDialog.show(RegisterActivity.this, null,
                                            "Please Wait....", true);
                                    mProgressDialog.setCancelable(true);

                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, "Check Internet Connection",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }else
                            {
                                Toast.makeText(RegisterActivity.this, "Password did not match", Toast.LENGTH_SHORT).show();
                                passswordEt.setText("");
                                confirmPassEt.setText("");
                            }
                        }else
                        {
                            confirmPassEt.setError("Enter password");
                        }

                    }else
                    {
                        passswordEt.setError("Enter password");
                    }
                }else
                {
                    emailEt.setError("Enter valid Email id");
                }


            }
        });
        alreadyRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, SignIn.class);
                startActivity(intent);



            }
        });
    }
    public void registration() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("email", emailEt.getText().toString()));
                data.add(new BasicNameValuePair("password",  passswordEt.getText().toString()));
                if (radiodoctor.isChecked()) {
                    data.add(new BasicNameValuePair("user_type", "2"));
                    new Web().requestPostStringData(AppUrl.registerUrl, data, RegisterActivity.this, 100);

                }else
                if (radiopatient.isChecked()) {
                    data.add(new BasicNameValuePair("user_type", "1"));
                    new Web().requestPostStringData(AppUrl.registerUrl, data, RegisterActivity.this, 100);

                }else
                {
                   Toast.makeText(RegisterActivity.this,"Please select Type",Toast.LENGTH_LONG).show();
                }




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
                        Toast.makeText(RegisterActivity.this, "Successfully Registered....", Toast.LENGTH_LONG).show();
                            JSONObject jObj2=obj.getJSONObject("login_data");
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("LOGIN", true);
                        editor.putString("USER_ID", jObj2.getString("user_id"));
                        editor.putString("USER_TYPE", jObj2.getString("user_type"));

                        editor.commit();

                        if(jObj2.getString("user_type").equals("Doctor"))
                        {
                            Intent intent = new Intent(RegisterActivity.this, BaseActivityForDoctor.class);
                            startActivity(intent);
                            finish();

                        }else
                        {
                            Intent intent = new Intent(RegisterActivity.this, TabHigherVersionActivity.class);
                            startActivity(intent);
                            finish();

                        }

                    } else {
                        DialogInterfacecustom.loginResponceDialog(RegisterActivity.this, obj.getString("message").toString(), "");


                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    void initView()
    {
        emailEt=(EditText)findViewById(R.id.emailEt);
        passswordEt=(EditText)findViewById(R.id.passwordEt);
        confirmPassEt=(EditText)findViewById(R.id.confirmPassEt);
        signupBtn= (Button)findViewById(R.id.signupBtn);
        alreadyRegisterBtn=(Button)findViewById(R.id.alreadyRegisterBtn);
        handler = new Handler();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
         radiodoctor=(RadioButton)findViewById(R.id.doctor);
         radiopatient=(RadioButton)findViewById(R.id.patient);

    }


}
