package com.example.test_11.doctorappointmentapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import Fragments.ReviewFrgment;

public class Main3Activity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        ReviewFrgment hello = new ReviewFrgment();
        fragmentTransaction.replace(R.id.fragment_container, hello,"hello");
        fragmentTransaction.commit();
      
    }

}
