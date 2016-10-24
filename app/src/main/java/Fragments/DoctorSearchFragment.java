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

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.test_11.doctorappointmentapp.DoctorDetailActivity;
import com.example.test_11.doctorappointmentapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import Adapters.DiseaseAdapter;
import Modal.DocterDetalModal;
import Modal.LocationModal;
import Util.AppLog;
import Util.AppUrl;
import Util.DialogInterfacecustom;
import web.CheckInternetConnectio;
import web.IResponse;
import web.Web;


public class DoctorSearchFragment extends Fragment implements  IResponse,AdapterView.OnItemSelectedListener {

    int attribute=0;

	private DatePicker datePicker;
	private Calendar calendar;
	private TextView dateView;
	private int year, month, day;

	private View mView;
	// List view
	private ListView lv;
	ProgressDialog pDialog;
	// Listview Adapter
	ArrayAdapter<String> adapter;
	LinearLayout lyt;
	// Search EditText
//	EditText inputSearch;
	private SearchView mSearchView;
DiseaseAdapter diseaseAdapter;
	private FragmentTabHost mTabHost;

	// ArrayList for Listview
	String TAG="Register";
	List<String> disease_list=new ArrayList<>();
	ProgressDialog mProgressDialog;
	private Handler handler;
	ImageView specialistBtn,locationBtn,conditionBtn,nameBtn,timeBtn;
	View child;
	private GoogleMap map;

	Marker marker;

	private Hashtable<String, String> markers;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	ArrayList<DocterDetalModal> doctorList=new ArrayList<>();
	ArrayList<LocationModal> locationList=new ArrayList<>();
//	ArrayList<LocationModal> specilityList=new ArrayList<>();


	public DoctorSearchFragment(){
		setRetainInstance(true);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			if(mView == null){
			mView = inflater.inflate(R.layout.doctor_search, container, false);

		}else{
			((ViewGroup) mView.getParent()).removeView(mView);
		}
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Search Doctor");

// Set an EditText view to get user input
		final SearchView input = new SearchView(getActivity());
		input.setIconified(false);
		alert.setView(input);

		alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				// Do something with value!
			}
		});



		alert.show();
		initView();
		setLyt(R.layout.location);

		locationBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setLyt(R.layout.location);
				attribute = 0;

				locationList.clear();
				getList(AppUrl.locationUrl);
				mProgressDialog = ProgressDialog.show(getActivity(), null,
						"Please Wait....", true);
				mProgressDialog.setCancelable(true);

			}
		});specialistBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setLyt(R.layout.specialist);
				attribute=1;

//				specilityList.clear();
				locationList.clear();
				getList(AppUrl.specilistUrl);
				mProgressDialog = ProgressDialog.show(getActivity(), null,
						"Please Wait....", true);
				mProgressDialog.setCancelable(true);


			}
		});conditionBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				setLyt(R.layout.condition);
				getList(AppUrl.conditionUrl);
				attribute = 2;
				locationList.clear();
				mProgressDialog = ProgressDialog.show(getActivity(), null,
						"Please Wait....", true);
				mProgressDialog.setCancelable(true);

			}
		});nameBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				attribute = 3;

				setLyt(R.layout.doctor_name);
				doctorNameSearch();
			}
		});
		timeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				attribute = 4;

				setLyt(R.layout.time);
				timeSearch();

			}
		});
			return mView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler();

		CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
				getActivity());

	/*	if(_checkInternetConnection.checkInterntConnection()) {


			getDoctorDetailList(AppUrl.doctordetailLocationUrl, "location","aliganj");

			mProgressDialog = ProgressDialog.show(getActivity(), null,
					"Please Wait....", true);
			mProgressDialog.setCancelable(true);
		}else
		{
			Toast.makeText(getActivity(), "Check Internet Connection",
					Toast.LENGTH_SHORT).show();


		}*/


	}

	void initView()
{
	lyt=(LinearLayout)mView.findViewById(R.id.root);
	specialistBtn = (ImageView) mView.findViewById(R.id.specialityBtn);
	nameBtn = (ImageView) mView.findViewById(R.id.nameBtn);
	conditionBtn = (ImageView) mView.findViewById(R.id.conditionBtn);
	timeBtn = (ImageView) mView.findViewById(R.id.timeBtn);
	locationBtn = (ImageView) mView.findViewById(R.id.locationBtn);
	calendar = Calendar.getInstance();
	year = calendar.get(Calendar.YEAR);
	month = calendar.get(Calendar.MONTH);
	day = calendar.get(Calendar.DAY_OF_MONTH);
	markers = new Hashtable<String, String>();
	imageLoader = ImageLoader.getInstance();
	initImageLoader();
	options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_launcher)		//	Display Stub Image
			.showImageForEmptyUri(R.drawable.ic_launcher)	//	If Empty image found
			.cacheInMemory()
			.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();


	//map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();

    	map=((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
		@Override
		public void onInfoWindowClick(Marker marker) {
			startActivity(new Intent(getActivity(), DoctorDetailActivity.class));
		}
	});
 }





	@Override
	public void onComplete(final String result,final int i) {
		// TODO Auto-generated method stub
		mProgressDialog.cancel();
       /* if(i==100)
		{*/
		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject obj = null;
				try {
					obj = new JSONObject(result);
					if (obj.has("error")) {
						DialogInterfacecustom.loginResponceDialog(getActivity(), obj.getString("message").toString(), "");
						return;
						}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AppLog.logPrint("valuecheck");
				if (i == 100) {
					if (attribute == 0) {
						try {
							JSONArray array = obj.getJSONArray("location");

							for (int i = 0; i < array.length(); i++) {
								JSONObject jObj1 = array.getJSONObject(i);
								locationList.add(new LocationModal(jObj1.getString("location_id"), jObj1.getString("location_name")));
							}

						} catch (JSONException e) {

						}
						locationSearch();
					} else if (attribute == 1) {
						try {
							JSONArray array = obj.getJSONArray("speciality");

							for (int i = 0; i < array.length(); i++) {
								JSONObject jObj1 = array.getJSONObject(i);
								locationList.add(new LocationModal(jObj1.getString("speciality_id"), jObj1.getString("speciality")));
							}

						} catch (JSONException e) {

						}
						specialistSearch();
					}
					if (attribute == 2) {
						try {
							JSONArray array = obj.getJSONArray("conditions");

							for (int i = 0; i < array.length(); i++) {
								JSONObject jObj1 = array.getJSONObject(i);
								locationList.add(new LocationModal(jObj1.getString("condition_id"), jObj1.getString("condition")));
							}

						} catch (JSONException e) {

						}
						conditiionsearch();
					}
				} else if (i == 200) {
					try {
						JSONArray array = obj.getJSONArray("data");

						for (int i = 0; i < array.length(); i++) {

							JSONObject jObj1 = array.getJSONObject(i);
							doctorList.add(new DocterDetalModal(jObj1.getString("UserID"), jObj1.getString("Name"), jObj1.getString("Address"), jObj1.getString("Pic"), new LatLng(Double.parseDouble(jObj1.getString("lat")), (Double.parseDouble(jObj1.getString("lng"))))));
						}


					} catch (JSONException e) {


					}

					if (map != null) {

						if (doctorList.size() > 0) {
							map.setInfoWindowAdapter(new CustomInfoWindowAdapter());


							for (int i = 0; i < doctorList.size(); i++) {
								final Marker kiel = map.addMarker(new MarkerOptions()
												.position(doctorList.get(i).getDoctor_clinic_latlong())
												.title(doctorList.get(i).getDoctor_name())
												.snippet(doctorList.get(i).getClinic_add())
								);
								markers.put(kiel.getId(), doctorList.get(i).getDoctor_img_url());
							}
							map.moveCamera(CameraUpdateFactory.newLatLngZoom(doctorList.get(0).getDoctor_clinic_latlong(), 15));
							map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
								}
					}

				}
			}

		});
	}

	public void updateCamera(float bearing) {

	/*	CameraPosition currentPlace = new CameraPosition.Builder()
				.target(new LatLng(centerLatitude, centerLongitude))
				.bearing(bearing).tilt(65.5f).zoom(18f).build();
		map.moveCamera(CameraUpdateFactory.newCameraPosition(currentPlace));*/

	}
	public void getList(final String url) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Web().requestGet(url, DoctorSearchFragment.this, 100);
			}
		}).start();
	}
	public void getDoctorDetailList(final String url,final String key,final String value) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<NameValuePair> data=new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair(key,value));
				new Web().requestPostStringData(url, data, DoctorSearchFragment.this, 200);

			}
		}).start();
	}



	public void onItemSelected(AdapterView parent, View view, int position, long id) {
		// On selecting a spinner item
		String item = parent.getItemAtPosition(position).toString();

		// Showing selected spinner item
		Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
		if(attribute==0) {
			map.clear();
			doctorList.clear();
			markers.clear();
			getDoctorDetailList(AppUrl.doctordetailLocationUrl, "location", locationList.get(position).getLocation_id());
		}else if(attribute==1) {
			map.clear();
			doctorList.clear();
			markers.clear();
			getDoctorDetailList(AppUrl.doctordetailSpecialistUrl, "speciality", locationList.get(position).getLocation_id());
		}else if(attribute==2) {
			map.clear();
			doctorList.clear();
			markers.clear();
			getDoctorDetailList(AppUrl.doctordetailConditionUrl, "conditions", locationList.get(position).getLocation_id());
		}


	}

	public void onNothingSelected(AdapterView arg0) {
		// TODO Auto-generated method stub

	}

	void setLyt(int root)
	{
		lyt.removeAllViews();
		 child = getActivity().getLayoutInflater().inflate(root,lyt, true);
	}
	void locationSearch()
	{
        locationBtn.setBackgroundResource(R.drawable.location_pressed);
		specialistBtn.setBackgroundResource(R.drawable.specilist);
		conditionBtn.setBackgroundResource(R.drawable.condition);
		nameBtn.setBackgroundResource(R.drawable.name);
		timeBtn.setBackgroundResource(R.drawable.time);

		Spinner spinner = (Spinner) child.findViewById(R.id.spinner);

		// Spinner click listener
		spinner.setOnItemSelectedListener(DoctorSearchFragment.this);
		// Spinner Drop down elements
		List<String> categories = new ArrayList<String>();
		for (int i = 0; i < locationList.size(); i++) {
			categories.add(locationList.get(i).getLocation_name());
			AppLog.logPrint(locationList.get(i).getLocation_name()+"....name");
		}
		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);

	}
	void specialistSearch()
	{

		locationBtn.setBackgroundResource(R.drawable.location);
		specialistBtn.setBackgroundResource(R.drawable.specilist_pressed);
		conditionBtn.setBackgroundResource(R.drawable.condition);
		nameBtn.setBackgroundResource(R.drawable.name);
		timeBtn.setBackgroundResource(R.drawable.time);

		Spinner spinner = (Spinner) child.findViewById(R.id.spinner);

		// Spinner click listener
		spinner.setOnItemSelectedListener(DoctorSearchFragment.this);
		// Spinner Drop down elements
		List<String> categories = new ArrayList<String>();
		for (int i = 0; i < locationList.size(); i++) {
			categories.add(locationList.get(i).getLocation_name());
			AppLog.logPrint(locationList.get(i).getLocation_name()+"....name");
		}

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);

	}
	void conditiionsearch()
	{
		locationBtn.setBackgroundResource(R.drawable.location);
		specialistBtn.setBackgroundResource(R.drawable.specilist);
		conditionBtn.setBackgroundResource(R.drawable.condition_pressed);
		nameBtn.setBackgroundResource(R.drawable.name);
		timeBtn.setBackgroundResource(R.drawable.time);

		Spinner spinner = (Spinner) child.findViewById(R.id.spinner);

		// Spinner click listener
		spinner.setOnItemSelectedListener(DoctorSearchFragment.this);
		// Spinner Drop down elements
		List<String> categories = new ArrayList<String>();
		for (int i = 0; i < locationList.size(); i++) {
			categories.add(locationList.get(i).getLocation_name());
			AppLog.logPrint(locationList.get(i).getLocation_name()+"....name");
		}
		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);

	}
	void doctorNameSearch()
	{
		locationBtn.setBackgroundResource(R.drawable.location);
		specialistBtn.setBackgroundResource(R.drawable.specilist);
		conditionBtn.setBackgroundResource(R.drawable.condition);
		nameBtn.setBackgroundResource(R.drawable.name_pressed);
		timeBtn.setBackgroundResource(R.drawable.time);

		final EditText docternameEt=(EditText)child.findViewById(R.id.doctorNameEt);

		child.findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), docternameEt.getText().toString(), Toast.LENGTH_SHORT).show();
				map.clear();
				doctorList.clear();
				markers.clear();
				getDoctorDetailList(AppUrl.doctordetailbynameUrl, "name", docternameEt.getText().toString());
			}
		});

	}
	void timeSearch()
	{
		locationBtn.setBackgroundResource(R.drawable.location);
		specialistBtn.setBackgroundResource(R.drawable.specilist);
		conditionBtn.setBackgroundResource(R.drawable.condition);
		nameBtn.setBackgroundResource(R.drawable.name);
		timeBtn.setBackgroundResource(R.drawable.time_pressed);
		final Button time=(Button)	child.findViewById(R.id.timeSelectBtn);
		final Button date=(Button)	child.findViewById(R.id.dateBtn);

		date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//setDate();
				new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener(){
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						int mon=monthOfYear+1;
						date.setText(new StringBuilder().append(day).append("/")
								.append(mon).append("/").append(year));
					}
				}, year, month, day).show();
			}
		});
		time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar mcurrentTime = Calendar.getInstance();
				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(Calendar.MINUTE);
				TimePickerDialog mTimePicker;
				mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
						time.setText(selectedHour + ":" + selectedMinute);
					}
				}, hour, minute, true);//Yes 24 hour time
				mTimePicker.setTitle("Select Time");
				mTimePicker.show();
			}
		});
	}


	private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

		private View view;

		public CustomInfoWindowAdapter() {
			view = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window,
					null);
		}

		@Override
		public View getInfoContents(Marker marker) {

			if (DoctorSearchFragment.this.marker != null
					&& DoctorSearchFragment.this.marker.isInfoWindowShown()) {
				DoctorSearchFragment.this.marker.hideInfoWindow();
				DoctorSearchFragment.this.marker.showInfoWindow();
			}
			return null;
		}

		@Override
		public View getInfoWindow(final Marker marker) {
			DoctorSearchFragment.this.marker = marker;

			String url = null;

			if (marker.getId() != null && markers != null && markers.size() > 0) {
				if ( markers.get(marker.getId()) != null &&
						markers.get(marker.getId()) != null) {
					url = markers.get(marker.getId());
				}
			}
			final ImageView image = ((ImageView) view.findViewById(R.id.badge));
		/*	view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(getActivity(), DoctorDetailActivity.class);
				}
			});*/

			if (url != null && !url.equalsIgnoreCase("null")
					&& !url.equalsIgnoreCase("")) {
				imageLoader.displayImage(url, image, options,
						new SimpleImageLoadingListener() {
							@Override
							public void onLoadingComplete(String imageUri,
														  View view, Bitmap loadedImage) {
								super.onLoadingComplete(imageUri, view,
										loadedImage);
								getInfoContents(marker);
							}
						});
			} else {
				image.setImageResource(R.drawable.ic_launcher);
			}

			final String title = marker.getTitle();
			final TextView titleUi = ((TextView) view.findViewById(R.id.title));
			if (title != null) {
				titleUi.setText(title);
			} else {
				titleUi.setText("");
			}

			final String snippet = marker.getSnippet();
			final TextView snippetUi = ((TextView) view
					.findViewById(R.id.snippet));
			if (snippet != null) {
				snippetUi.setText(snippet);
			} else {
				snippetUi.setText("");
			}


			return view;
		}
	}

	private void initImageLoader() {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager)
					getActivity().getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024;
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}

		final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity()).threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(memoryCacheSize)
				.memoryCache(new FIFOLimitedMemoryCache(memoryCacheSize-1000000))
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
				.build();

		ImageLoader.getInstance().init(config);
	}

	}
