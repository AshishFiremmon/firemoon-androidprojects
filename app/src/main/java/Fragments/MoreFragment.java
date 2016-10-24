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

package Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.test_11.doctorappointmentapp.DoctorDetailActivity;
import com.example.test_11.doctorappointmentapp.DoctorPorileActivity;
import com.example.test_11.doctorappointmentapp.R;
import com.example.test_11.doctorappointmentapp.SignIn;


public class MoreFragment extends Fragment {

	private View mView;

	public MoreFragment(){
		setRetainInstance(true);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if(mView == null){
			mView = inflater.inflate(R.layout.view_tab_one, container, false);

		}else{
//			((ViewGroup) mView.getParent()).removeView(mView);
		}
		mView.findViewById(R.id.logoutTxt).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logout();
				getActivity().finish();
				startActivity(new Intent(getActivity(),SignIn.class));
			}
		});
		mView.findViewById(R.id.shareTxt).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sharingIntent = new Intent(Intent.ACTION_SEND);
				sharingIntent.setType("text/html");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>Consultation Simplified</p>"));
				startActivity(Intent.createChooser(sharingIntent,"Share using"));
			}
		});
		mView.findViewById(R.id.aboutBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					startActivity(new Intent(getActivity(),DoctorDetailActivity.class));
			}
		});
		return mView;
	}
	public  void logout(){
		SharedPreferences sharedpreferences = getActivity().getSharedPreferences(SignIn.MyPREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.clear();
		editor.commit();
		DoctorPorileActivity.login=false;
	}
}
