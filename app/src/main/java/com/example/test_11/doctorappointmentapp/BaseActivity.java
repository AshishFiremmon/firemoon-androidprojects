/*
 * Copyright 2013 - learnNcode (learnncode@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.test_11.doctorappointmentapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import Fragments.DoctorSearchFragment;
import Fragments.MoreFragment;
import Fragments.ProfileFragment;
import Fragments.RecordFrgment;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.activity_main2);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);


		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
*/		setContentView(R.layout.activity_base);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);


		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

			mTabHost  = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("", getResources().getDrawable(R.drawable.find_btn)), DoctorSearchFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("",getResources().getDrawable(R.drawable.record_btn)), RecordFrgment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("",getResources().getDrawable(R.drawable.profile_btn)), ProfileFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("",getResources().getDrawable(R.drawable.more_btn)), MoreFragment.class, null);

		mTabHost.setCurrentTabByTag("tag1");

		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {

		/*	TextView textView = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
			if(textView.getLayoutParams() instanceof RelativeLayout.LayoutParams){

				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				params.addRule(RelativeLayout.CENTER_VERTICAL);
				params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
				params.width  = RelativeLayout.LayoutParams.WRAP_CONTENT;
				mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title).setLayoutParams(params);

			}else if(textView.getLayoutParams() instanceof LinearLayout.LayoutParams){
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
				params.gravity = Gravity.CENTER;
				mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title).setLayoutParams(params);
			}*/
			View v =  mTabHost.getTabWidget().getChildAt(i);

			// Look for the title view to ensure this is an indicator and not a divider.
			TextView tv = (TextView)v.findViewById(android.R.id.title);
			if(tv == null) {
				continue;
			}
			v.setBackgroundColor(Color.TRANSPARENT);
		}

	/*	mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {

				android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
				DoctorSearchFragment doctorSearchFragment = (DoctorSearchFragment) fragmentManager.findFragmentByTag("tab1");
				RecordFrgment tabTwoFrgment = (RecordFrgment) fragmentManager.findFragmentByTag("tab2");
				ProfileFragment Profile = (ProfileFragment) fragmentManager.findFragmentByTag("tab3");
				MoreFragment more = (MoreFragment) fragmentManager.findFragmentByTag("tab4");

				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


				if(tabId.equalsIgnoreCase("tab1")){
					if(doctorSearchFragment != null){
						if(tabTwoFrgment != null){
							fragmentTransaction.hide(tabTwoFrgment);
						}
						if(Profile != null){
							fragmentTransaction.hide(Profile);
						}
						if(more != null){
							fragmentTransaction.hide(more);
						}
						fragmentTransaction.show(doctorSearchFragment);
					}
				}else if(tabId.equalsIgnoreCase("tab2"))

				{
					if(tabTwoFrgment != null){
						if(doctorSearchFragment != null){
							fragmentTransaction.hide(doctorSearchFragment);
						}
						if(Profile != null){
							fragmentTransaction.hide(Profile);
						}
						if(more != null){
							fragmentTransaction.hide(more);
						}
						fragmentTransaction.show(tabTwoFrgment);
					}
				}else if(tabId.equalsIgnoreCase("tab3"))

				{
					if(Profile != null){
						if(doctorSearchFragment != null){
							fragmentTransaction.hide(doctorSearchFragment);
						}
						if(tabTwoFrgment != null){
							fragmentTransaction.hide(tabTwoFrgment);
						}
						if(more != null){
							fragmentTransaction.hide(more);
						}
						fragmentTransaction.show(Profile);
					}
				}else

				{
					if(more != null){
						if(doctorSearchFragment != null){
							fragmentTransaction.hide(doctorSearchFragment);
						}
						if(tabTwoFrgment != null){
							fragmentTransaction.hide(tabTwoFrgment);
						}
						if(Profile != null){
							fragmentTransaction.hide(Profile);
						}

						fragmentTransaction.show(more);
					}
				}
				fragmentTransaction.commit();         
			}
		});*/
	}
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_camara) {
			// Handle the camera action
		} else if (id == R.id.nav_gallery) {

		} else if (id == R.id.nav_slideshow) {

		} else if (id == R.id.nav_manage) {

		} /*else if (id == R.id.nav_share) {

		} else if (id == R.id.nav_send) {

		}*/

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
