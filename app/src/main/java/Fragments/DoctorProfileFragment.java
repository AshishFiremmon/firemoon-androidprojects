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

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test_11.doctorappointmentapp.DoctorPorileActivity;
import com.example.test_11.doctorappointmentapp.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Util.AppLog;
import Util.AppUrl;
import Util.DialogInterfacecustom;
import web.IResponse;
import web.Web;


public class DoctorProfileFragment extends Fragment implements IResponse {

    private View mView;
    ImageView edit1,edit2,edit3,edit4,edit5,editTop;
    Button editBtn,backBtn;
    EditText addEt,nameEt;
   static EditText dobEt;
    RelativeLayout bloodgroupLyt;
    TextView bloadgroup;
    private int year;
    private int month;
    private int day;

    static final int DATE_PICKER_ID = 1111;
    RadioButton radioMaleBtn,radioFemaleBtn;
    ProgressDialog mProgressDialog;
    private Handler handler;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
ImageView profiledp;
    private final static int ACTIVITY_PICK_IMAGE = 0;
    private final static int ACTIVITY_TAKE_PHOTO = 1;
    File destination;
    EditText name,qualification,specialisation,time,email,mobile,city,clinicadd,fee,registration,experience;

    public DoctorProfileFragment() {
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_doctor_porile, container, false);
            initView();
        } else {
//			((ViewGroup) mView.getParent()).removeView(mView);
        }
        editBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       startActivity(new Intent(getActivity(), DoctorPorileActivity.class));
    }
});/*
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editGone();
            }
        });
        bloodgroupLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectionBloodGroupDialog();


            }
        });

        dobEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });
        profiledp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.image_picker_dialog);
                dialog.setTitle("Choose Profile Image");
                dialog.findViewById(R.id.camerabtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraIntent();
                        dialog.hide();
                    }
                });

                dialog.findViewById(R.id.galleryBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        galleryIntent();
                        dialog.hide();
                    }
                });

                dialog.show();

            }
        });
        CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(
                getActivity());

        if(_checkInternetConnection.checkInterntConnection())
        {
            getPayientProfile();
            mProgressDialog = ProgressDialog.show(getActivity(), null,
                    "Please Wait....", true);
            mProgressDialog.setCancelable(true);

        }
        else {
            Toast.makeText(getActivity(), "Check Internet Connection",
                    Toast.LENGTH_SHORT).show();

        }
   */     return mView;
    }

    void initView()
    {

        editBtn=(Button)mView.findViewById(R.id.edit);

        qualification=(EditText)mView.findViewById(R.id.qualificationEt);
        specialisation=(EditText)mView.findViewById(R.id.specialisationEt);
        time=(EditText)mView.findViewById(R.id.clinictimeEt);
        email=(EditText)mView.findViewById(R.id.emailEt);
        mobile=(EditText)mView.findViewById(R.id.phonenoEt);
        city=(EditText)mView.findViewById(R.id.cityEt);
        clinicadd=(EditText)mView.findViewById(R.id.clinicaddEt);
        fee=(EditText)mView.findViewById(R.id.feeEt);
        registration=(EditText)mView.findViewById(R.id.registrationnoEt);
        experience=(EditText)mView.findViewById(R.id.experienceEt);
        name=(EditText)mView.findViewById(R.id.nameEt);

        qualification.setText(DoctorPorileActivity.qualification1);
        specialisation.setText(DoctorPorileActivity.specialisation1);
        time.setText(DoctorPorileActivity.time1);
        email.setText(DoctorPorileActivity.email1);
        mobile.setText(DoctorPorileActivity.mobile1);
        city.setText(DoctorPorileActivity.city1);
        clinicadd.setText(DoctorPorileActivity.clinicadd1);
        fee.setText(DoctorPorileActivity.fee1);
        registration.setText(DoctorPorileActivity.registration1);
        experience.setText(DoctorPorileActivity.experience1);
        name.setText(DoctorPorileActivity.name1);


    /*
        profiledp=(ImageView)mView.findViewById(R.id.profiledp);
        bloadgroup  =(TextView)mView.findViewById(R.id.bloodgroupTxt);

        edit1=(ImageView)mView.findViewById(R.id.edtibtn1);
//        edit2=(ImageView)mView.findViewById(R.id.edtibtn2);
        edit3=(ImageView)mView.findViewById(R.id.edtibtn3);
        edit4=(ImageView)mView.findViewById(R.id.edtibtn4);
        edit5=(ImageView)mView.findViewById(R.id.edtibtn5);
        backBtn=(Button)mView.findViewById(R.id.back);
        dobEt=(EditText)mView.findViewById(R.id.dobEt);
        nameEt=(EditText)mView.findViewById(R.id.nameEt);
        addEt=(EditText)mView.findViewById(R.id.mobileEt);
        bloodgroupLyt=(RelativeLayout)mView.findViewById(R.id.bloodgroupLyt);
        bloodgroupLyt.setEnabled(false);
        radioMaleBtn=(RadioButton)mView.findViewById(R.id.radioButton);
        radioFemaleBtn=(RadioButton)mView.findViewById(R.id.radioButton1);

        dobEt.setEnabled(false);
        nameEt.setEnabled(false);
        addEt.setEnabled(false);
        profiledp.setEnabled(false);
        handler=new Handler();
        imageLoader = ImageLoader.getInstance();
        initImageLoader();
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher)		//	Display Stub Image
                .showImageForEmptyUri(R.drawable.ic_launcher)	//	If Empty image found
                .cacheInMemory()
                .cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
*/
    }
    void editVisibile()
    {
        edit1.setVisibility(View.VISIBLE);
        edit3.setVisibility(View.VISIBLE);
        edit4.setVisibility(View.VISIBLE);
        edit5.setVisibility(View.VISIBLE);
        editBtn.setText("Save");
        backBtn.setVisibility(View.VISIBLE);
        dobEt.setEnabled(true);
        nameEt.setEnabled(true);
        addEt.setEnabled(true);
        bloodgroupLyt.setEnabled(true);
        radioMaleBtn.setEnabled(true);
        radioFemaleBtn.setEnabled(true);
        profiledp.setEnabled(true);
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, ACTIVITY_TAKE_PHOTO);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), ACTIVITY_PICK_IMAGE);
    }
    public void getPayientProfile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<NameValuePair> data=new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("PateintID","605"));
                new Web().requestPostStringData(AppUrl.patientprofileUrl, data, DoctorProfileFragment.this, 200);

            }
        }).start();
    }
    public void updateProfile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                try {
                    FileBody bin = new FileBody(destination);
                    entity.addPart("image", bin);
                    entity.addPart("PateintID", new StringBody("605"));
                    entity.addPart("Name", new StringBody(nameEt.getText().toString()));
                    entity.addPart("BloodGroup", new StringBody(bloadgroup.getText().toString()));
                    if(radioMaleBtn.isChecked()) {
                        entity.addPart("Gender", new StringBody("male"));
                    }else   if(radioFemaleBtn.isChecked()) {
                        entity.addPart("Gender", new StringBody("female"));
                    }
                    entity.addPart("Dob", new StringBody(dobEt.getText().toString()));
                    entity.addPart("Mobile", new StringBody(addEt.getText().toString()));
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
                new Web().postDataWithMultipart(AppUrl.updatepatientprofileUrl, entity, DoctorProfileFragment.this, 100);
            }
        }).start();
    }

    void editGone()
    {
        edit1.setVisibility(View.GONE);
        edit3.setVisibility(View.GONE);
        edit4.setVisibility(View.GONE);
        edit5.setVisibility(View.GONE);
        backBtn.setVisibility(View.GONE);
        dobEt.setEnabled(false);
        nameEt.setEnabled(false);
        addEt.setEnabled(false);
        editBtn.setText("Edit");
        profiledp.setEnabled(false);
        bloodgroupLyt.setEnabled(false);
        radioMaleBtn.setEnabled(false);
        radioFemaleBtn.setEnabled(false);

    }
    void selectionBloodGroupDialog()
    {
        final String items[]={"A+","A-","B+","B-","AB+","AB-","O-","O+"};
        new AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(items, 0, null)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        bloadgroup.setText(items[selectedPosition]);
                        // Do something useful withe the position of the selected radio button
                    }
                })
                .show();
    }

    @Override
    public void onComplete(final String result,final int i) {
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
                if (i == 200) {
                    try {
                        JSONArray array = obj.getJSONArray("Patient_data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jObj1 = array.getJSONObject(i);
                            AppLog.logPrint(jObj1.getString("UserID"));
                            nameEt.setText(jObj1.getString("Name"));
                            dobEt.setText(jObj1.getString("Dob"));
                            addEt.setText(jObj1.getString("Mobile"));
                            bloadgroup.setText(jObj1.getString("BloodGroup"));
                            if (jObj1.getString("BloodGroup").equals("male")) {
                                radioMaleBtn.setChecked(true);
                            } else if (jObj1.getString("BloodGroup").equals("female")) {
                                radioFemaleBtn.setChecked(true);
                            }
                            imageLoader.displayImage(jObj1.getString("Image"), profiledp, options,
                                    new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri,
                                                                      View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view,
                                                    loadedImage);
                                        }
                                    });
                        }

                    } catch (JSONException e) {

                    }
                }
            }

        });
    }


    public  static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            int mon=month+1;
            dobEt.setText(new StringBuilder().append(day).append("/")
                    .append(mon).append("/").append(year));
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ACTIVITY_PICK_IMAGE) {
                Uri selectedImage = data.getData();
                String path=getPath(selectedImage,getActivity());
                destination=new File(path);
                    onSelectFromGalleryResult(data);
            }
            else if (requestCode == ACTIVITY_TAKE_PHOTO)

                onCaptureImageResult(data);

        }
    }
    public static String getPath(Uri uri,Context con)
    {
        if( uri == null)
        {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = con.getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null )
        {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        Bitmap bitmap=Bitmap.createScaledBitmap(thumbnail,100,100,true);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profiledp.setImageBitmap(thumbnail);


    }
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profiledp.setImageBitmap(bm);
    }
}
