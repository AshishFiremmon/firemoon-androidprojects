package com.example.test_11.doctorappointmentapp;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import Fragments.DoctorSearchFragment;
import Fragments.MoreFragment;
import Fragments.ProfileFragment;
import Fragments.RecordFrgment;

public class TabHigherVersionActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private LinearLayout container;
    TextView toptxt;
    Button editBtn,backBtn;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_higherversion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        container = (LinearLayout) findViewById(R.id.fragment_container);

        setSupportActionBar(toolbar);
    /*    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
*/       // navigationView.setNavigationItemSelectedListener(this);
//        toptxt=(TextView)findViewById(R.id.toptxt);
     /*   editBtn=(Button)findViewById(R.id.edtibtntop);
        backBtn=(Button)findViewById(R.id.back);
    editBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(editBtn.getText().equals("Edit")) {
            (new ProfileFragment()).editVisibile();
            editBtn.setText("Save");
        }else  if(editBtn.getText().equals("Save"))
        {
            (new ProfileFragment()).updateProfile();

        }
    }
});
*/

       /* backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new ProfileFragment()).  editGone();
                backBtn.setVisibility(View.GONE);
            }
        });*/
        //create tabs title
   /*     tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.find_btn));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.record_btn));

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.profile_btn));

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.more_btn));
*///        final DoctorSearchFragment doctor=  new DoctorSearchFragment();

        //replace default fragment
        replaceFragment(new DoctorSearchFragment(),"hello");
//        toptxt.setText("Search");
        View view1 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.find_btn);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));

        View view2 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.record_btn);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));


        View view3 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.profile_btn);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));
        View view4 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        view4.findViewById(R.id.icon).setBackgroundResource(R.drawable.more_btn);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view4));

        //handling tab click event
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(new DoctorSearchFragment(),"hello");
//                    toptxt.setText("Search");
                } else if (tab.getPosition() == 1) {
                    replaceFragment(new RecordFrgment(),"hello1");
//                    toptxt.setText("Record");
                } else if (tab.getPosition() == 2){
                    replaceFragment(new ProfileFragment(),"hello2");
//                    toptxt.setText("Profile");
                }else
                {
                    replaceFragment(new MoreFragment(),"hello3");
//                    toptxt.setText("More");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

   private void replaceFragment(Fragment fragment,String str) {
        FragmentManager fragmentManager = getSupportFragmentManager();
       FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
      /* if(!str.equals("hello"))
        transaction.addToBackStack(str);
  */      transaction.commit();
    }
}
