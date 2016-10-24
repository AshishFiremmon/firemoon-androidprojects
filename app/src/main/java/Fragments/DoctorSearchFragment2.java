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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.test_11.doctorappointmentapp.DoctorListActivity;
import com.example.test_11.doctorappointmentapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.DiseaseAdapter;
import Util.AppUrl;
import web.CheckInternetConnectio;
import web.IResponse;
import web.Web;


public class DoctorSearchFragment2 extends Fragment implements SearchView.OnQueryTextListener, IResponse {

	private View mView;
	// List view
	private ListView lv;
	ProgressDialog pDialog;
	// Listview Adapter
	ArrayAdapter<String> adapter;

	// Search EditText
//	EditText inputSearch;
	private SearchView mSearchView;
DiseaseAdapter diseaseAdapter;
	// ArrayList for Listview
	String TAG="Register";
	List<String> disease_list=new ArrayList<>();
	ProgressDialog mProgressDialog;
	private Handler handler;

	public DoctorSearchFragment2(){
		setRetainInstance(true);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		handler = new Handler();

		if(mView == null){
			mView = inflater.inflate(R.layout.doctor_search, container, false);

		}else{
//			((ViewGroup) mView.getParent()).removeView(mView);
		}

	/*	mSearchView = (SearchView) mView.findViewById(R.id.search_view);
		lv = (ListView) mView.findViewById(R.id.list_view);*/
disease_list.clear();


		CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
				getActivity());

		if(_checkInternetConnection.checkInterntConnection())
		{

/*
getDiseasList();
			mProgressDialog = ProgressDialog.show(getActivity(), null,
					"Please Wait....", true);
			mProgressDialog.setCancelable(true);
*/


		}
		else {
			Toast.makeText(getActivity(), "Check Internet Connection",
					Toast.LENGTH_SHORT).show();

		}

		lv.setTextFilterEnabled(true);
		setupSearchView();
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
									  @Override
									  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
										  startActivity(new Intent(getActivity(), DoctorListActivity.class));
									  }
								  }
		);
		return mView;
	}
	private void setupSearchView() {
		mSearchView.setIconifiedByDefault(false);
		mSearchView.setOnQueryTextListener(this);
//		mSearchView.setSubmitButtonEnabled(true);
		mSearchView.setQueryHint("Search Here");
//		lv.setTextFilterEnabled(false);
	}

	public boolean onQueryTextChange(String newText) {
		/*if (TextUtils.isEmpty(newText)) {
			lv.clearTextFilter();
		} else {
			lv.setFilterText(newText.toString());
		}*/
		try {
			ArrayAdapter ca = (ArrayAdapter)lv.getAdapter();

			if (TextUtils.isEmpty(newText)) {
				System.out.println("isEmpty");
				//listview.clearTextFilter();
				ca.getFilter().filter(null);
			} else {

				ca.getFilter().filter(newText);
				//listview.setFilterText(newText);

			}
		}catch (Exception e)
		{

		}

		return true;
	}

	public boolean onQueryTextSubmit(String query) {
		return false;
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
					JSONArray array=obj.getJSONArray("data");

					for (int i = 0; i <array.length() ; i++) {

						JSONObject jObj1=array.getJSONObject(i);
						disease_list.add(jObj1.getString("disease_name"));

					}

				}catch (JSONException e)
				{

				}
				adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.product_name, disease_list);

				lv.setAdapter(adapter);

			}
		});
	}
	public void getDiseasList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				new Web().requestGet(AppUrl.diseaseUrl,  DoctorSearchFragment2.this, 100);



			}
		}).start();
	}
}
