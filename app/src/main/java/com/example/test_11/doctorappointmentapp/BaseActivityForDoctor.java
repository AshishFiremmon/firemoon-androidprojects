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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;


public class BaseActivityForDoctor extends FragmentActivity {

	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_doctor);

		findViewById(R.id.CalenderBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseActivityForDoctor.this, Main2Activity.class);
				startActivity(intent);


			}
		});
		findViewById(R.id.massageBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseActivityForDoctor.this, Main4Activity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.bookinBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseActivityForDoctor.this, Main3Activity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.transactionbtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseActivityForDoctor.this, Main2Activity.class);
				startActivity(intent);
//				finish();

			}
		});
		findViewById(R.id.profileBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseActivityForDoctor.this, DoctorPorileActivity.class);
				startActivity(intent);
//				finish();
			}
		});
		findViewById(R.id.btnlogout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logout();
				finish();
				startActivity(new Intent(BaseActivityForDoctor.this,SignIn.class));

			}
		});
	}
	public  void logout(){
		SharedPreferences sharedpreferences = getSharedPreferences(SignIn.MyPREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.clear();
		editor.commit();
		DoctorPorileActivity.login=false;
	}
}
