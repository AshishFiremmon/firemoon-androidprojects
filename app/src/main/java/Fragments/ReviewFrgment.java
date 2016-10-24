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
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test_11.doctorappointmentapp.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Modal.DocterBookedModal;
import Util.AppLog;
import Util.AppUrl;
import Util.DialogInterfacecustom;
import web.IResponse;
import web.Web;


public class ReviewFrgment extends Fragment implements IResponse{

	private View mView;
	ListView lv;
	ArrayList<DocterBookedModal> list=new ArrayList<>();
	ProgressDialog mProgressDialog;
	private Handler handler;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;


	public ReviewFrgment(){
		setRetainInstance(true);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
list.clear();
		if(mView == null){
			mView = inflater.inflate(R.layout.review_screen, container, false);

		}else{
//			((ViewGroup) mView.getParent()).removeView(mView);
		}
	/*	for (int i = 0; i <6 ; i++) {
			list.add(new DocterBookedModal("","","",""));
		}*/
		initView();
		MyAdapter adapter = new MyAdapter(getActivity());
		lv.setAdapter(adapter);
	/*	CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
				getActivity());

		if(_checkInternetConnection.checkInterntConnection())
		{
			getBookedDoctorList();
			mProgressDialog = ProgressDialog.show(getActivity(), null,
					"Please Wait....", true);
			mProgressDialog.setCancelable(true);

		}
		else {
			Toast.makeText(getActivity(), "Check Internet Connection",
					Toast.LENGTH_SHORT).show();

		}*/

		return mView;
	}
	void initView()
	{
		lv = (ListView)mView.findViewById(R.id.listView1);
        handler=new Handler();
		imageLoader = ImageLoader.getInstance();
		initImageLoader();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)		//	Display Stub Image
				.showImageForEmptyUri(R.drawable.ic_launcher)	//	If Empty image found
				.cacheInMemory()
				.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
	}
	public void getBookedDoctorList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<NameValuePair> data=new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("PateintID","2"));
				new Web().requestPostStringData(AppUrl.bookeddoctorUrl, data, ReviewFrgment.this, 200);

			}
		}).start();
	}

	@Override
	public void onComplete(final String result, int i) {
		mProgressDialog.cancel();
		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject obj = null;
				try {
					obj = new JSONObject(result);
					if (obj.has("error") && obj.getString("error").equals("true")) {
						DialogInterfacecustom.loginResponceDialog(getActivity(), obj.getString("message").toString(), "");
						return;
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JSONArray array = obj.getJSONArray("doctor_list");
					AppLog.logPrint("valuecheckjjjjj"+array.length());

					for (int i = 0; i < array.length(); i++) {
						JSONObject jObj1 = array.getJSONObject(i);
						list.add(new DocterBookedModal(jObj1.getString("UserID"), jObj1.getString("Name"), jObj1.getString("Address"), jObj1.getString("Pic")));
						AppLog.logPrint(jObj1.getString("UserID"));

					}

				} catch (JSONException e) {

				}
				MyAdapter adapter = new MyAdapter(getActivity());
				lv.setAdapter(adapter);

			}

		});
	}

	public class MyAdapter extends BaseAdapter {


		public MyAdapter(Context context)
		{

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final  int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView tv1, tv2;
			ImageView doctorDp;
			LayoutInflater inflater;
			inflater = LayoutInflater.from(getActivity());
			View row1 = inflater.inflate(R.layout.review_list, parent, false);
			/*tv1 = (TextView) row1.findViewById(R.id.doctorNameTxt);
			tv2 = (TextView) row1.findViewById(R.id.doctorAddTxt);
			doctorDp = (ImageView) row1.findViewById(R.id.doctorDp);
			tv1.setText(list.get(position).getDoctor_name());
			tv2.setText(list.get(position).getClinic_add());
			imageLoader.displayImage(list.get(position).getDoctor_img_url(), doctorDp, options,
					new SimpleImageLoadingListener() {
						@Override
						public void onLoadingComplete(String imageUri,
													  View view, Bitmap loadedImage) {
							super.onLoadingComplete(imageUri, view,
									loadedImage);
						}
					});*/
			return row1;
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
