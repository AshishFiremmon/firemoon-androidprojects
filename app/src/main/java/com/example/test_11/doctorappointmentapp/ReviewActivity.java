package com.example.test_11.doctorappointmentapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ReviewActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    EditText msgEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        msgEt=(EditText)findViewById(R.id.massageBoxEt);

        findViewById(R.id.msgsendBtn).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        msgEt.setText("");
        Toast.makeText(ReviewActivity.this,"Massage send .....",Toast.LENGTH_LONG).show();
    }
});
    }

}