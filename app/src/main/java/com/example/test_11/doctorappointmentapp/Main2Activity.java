package com.example.test_11.doctorappointmentapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import Fragments.ReviewFrgment;

public class Main2Activity extends Activity
        {
            FragmentTransaction fragmentTransaction;
            FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     */   fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
		ReviewFrgment hello = new ReviewFrgment();
		fragmentTransaction.replace(R.id.fragment_container, hello,"hello");
		fragmentTransaction.commit();


    }


}
