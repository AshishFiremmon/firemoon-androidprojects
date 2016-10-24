package com.example.test_11.doctorappointmentapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import Util.Validation;

public class DoctorPorileActivity extends AppCompatActivity {
    EditText name, qualification,specialisation,time,email,mobile,city,clinicadd,fee,registration,experience;
    ImageView dp;
    private final static int ACTIVITY_PICK_IMAGE = 0;
    private final static int ACTIVITY_TAKE_PHOTO = 1;
    File destination;

    public static String name1,qualification1,specialisation1,time1,email1,mobile1,city1,clinicadd1,fee1,registration1,experience1;
public  static  boolean login;


    String timetxt;
    TextView txt,txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,txt11,txt12,txt13,txt14;
    String mon="Mon";
    String tues="Tues";
    String wed="Wed";
    String thur="Thur";
    String fri="Fri";
    String sat="Sat";
    String sun="Sun";
    String str="Time :-";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_porile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        qualification=(EditText)findViewById(R.id.qualificationEt);
        specialisation=(EditText)findViewById(R.id.specialisationEt);
        time=(EditText)findViewById(R.id.clinictimeEt);
        email=(EditText)findViewById(R.id.emailEt);
        mobile=(EditText)findViewById(R.id.phonenoEt);
        city=(EditText)findViewById(R.id.cityEt);
        clinicadd=(EditText)findViewById(R.id.clinicaddEt);
        fee=(EditText)findViewById(R.id.feeEt);
        registration=(EditText)findViewById(R.id.registrationnoEt);
        experience=(EditText)findViewById(R.id.experienceEt);
        name=(EditText)findViewById(R.id.nameEt);


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

        dp=(ImageView)findViewById(R.id.profiledp);

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(DoctorPorileActivity.this);
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

        qualification.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final String items[] = {"BAMS", "BHMS", "BDS", "BPTh/BPT"};
        new AlertDialog.Builder(DoctorPorileActivity.this)
                .setSingleChoiceItems(items, 0, null)

                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        // Do something useful withe the position of the selected radio button
                        qualification.setText(items[selectedPosition]);
                    }
                })

                .show();

    }
});
        specialisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = {"Ayurveda", "Cardiologist", "Dentist", "Orthdontist"};
                new AlertDialog.Builder(DoctorPorileActivity.this)
                        .setSingleChoiceItems(items, 0, null)

                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                // Do something useful withe the position of the selected radio button
                                specialisation.setText(items[selectedPosition]);
                            }
                        })

                        .show();


            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DoctorPorileActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        time.setText(selectedHour + ":" + selectedMinute);


                        timetxt=selectedHour + ":" + selectedMinute;

                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(DoctorPorileActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        time.setText(selectedHour + ":" + selectedMinute);


                                timetxt+=" - "+selectedHour + ":" + selectedMinute;

                                time.setText(timetxt);
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("To");
                        mTimePicker.show();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("From");
                mTimePicker.show();
*/


//                startActivity(new Intent(DoctorPorileActivity.this,AddTimingActivity.class));

                str="";
                final Dialog dialog = new Dialog(DoctorPorileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // Include dialog.xml file
                dialog.setContentView(R.layout.activity_time_screen);
                // Set dialog title

                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.save);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
time.setText(str);

                        dialog.dismiss();
//                        finish();
                    }
                });

            }
        });
        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1=name.getText().toString();

                qualification1=qualification.getText().toString();
                specialisation1=specialisation.getText().toString();
                time1=time.getText().toString();
                email1=email.getText().toString();
               mobile1=mobile.getText().toString();
                city1=city.getText().toString();
                clinicadd1=clinicadd.getText().toString();
                fee1=fee.getText().toString();
                registration1=registration.getText().toString();
                experience1=experience.getText().toString();;

                if(TextUtils.isEmpty(name1) || TextUtils.isEmpty(qualification1) ||TextUtils.isEmpty(specialisation1) ||TextUtils.isEmpty(time1) ||TextUtils.isEmpty(email1) ||TextUtils.isEmpty(mobile1) ||TextUtils.isEmpty(city1) ||TextUtils.isEmpty(clinicadd1) ||TextUtils.isEmpty(fee1) ||TextUtils.isEmpty(registration1) ||TextUtils.isEmpty(experience1) ) {
                    Toast.makeText(DoctorPorileActivity.this, "All Field are required", Toast.LENGTH_SHORT).show();

                }else if(!Validation.isEmailAddress(email,true))
                {
                    Toast.makeText(DoctorPorileActivity.this, "Invalid email Id", Toast.LENGTH_SHORT).show();

                }
                else if(!Validation.isMobileNoValid(mobile1))
                {
                    Toast.makeText(DoctorPorileActivity.this, "Invalid no.", Toast.LENGTH_SHORT).show();

                }else
                {
                    login=true;
                    startActivity(new Intent(DoctorPorileActivity.this, BaseActivityForDoctor.class));
                    finish();
                }
            }
        });
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
    public   void timeSelect( View  v)
    {
        txt=(TextView)v;
        timetxt="";
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(DoctorPorileActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        time.setText(selectedHour + ":" + selectedMinute);


                timetxt=selectedHour + ":" + selectedMinute;

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DoctorPorileActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                        timetxt+=" - "+selectedHour + ":" + selectedMinute;

                        txt.setText(timetxt);

                        if(txt.getId()==R.id.timetxt1 ||txt.getId()==R.id.timetxt2 )
                        {
                           mon+="\n"+timetxt;
//                            time.setText(time.getText().toString()+"\n"+mon);

                        }
                        if(txt.getId()==R.id.timetxt3 ||txt.getId()==R.id.timetxt4 )
                        {
                            str+="\n"+(tues+="\n"+timetxt);
//                            time.setText(time.getText().toString()+"\n"+tues);

                        }
                        if(txt.getId()==R.id.timetxt5 ||txt.getId()==R.id.timetxt6 )
                        {
                            str+="\n"+(wed+="\n"+timetxt);
//                            time.setText(time.getText().toString()+"\n"+wed);

                        }  if(txt.getId()==R.id.timetxt7 ||txt.getId()==R.id.timetxt8 )
                        {
                            str+="\n"+(thur+="\n"+timetxt);
//                            time.setText(time.getText().toString()+"\n"+thur);

                        }  if(txt.getId()==R.id.timetxt9 ||txt.getId()==R.id.timetxt10 )
                        {
                            str+="\n"+(fri+="\n"+timetxt);
//                            time.setText(time.getText().toString()+"\n"+fri);

                        }  if(txt.getId()==R.id.timetxt11 ||txt.getId()==R.id.timetxt12 )
                        {
                            str+="\n"+(sat+="\n"+timetxt);
//                            time.setText(time.getText().toString()+"\n"+sat);

                        }  if(txt.getId()==R.id.timetxt13 ||txt.getId()==R.id.timetxt14 )
                        {
                            str+="\n"+(sun+="\n"+timetxt);
//                            time.setText(time.getText().toString()+"\n"+sun);

                        }

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("To");
                mTimePicker.show();
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("From");
        mTimePicker.show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ACTIVITY_PICK_IMAGE) {
                Uri selectedImage = data.getData();
                String path=getPath(selectedImage,this);

                destination=new File(path);

//                loginData();
                onSelectFromGalleryResult(data);
            }
            else if (requestCode == ACTIVITY_TAKE_PHOTO) {

                onCaptureImageResult(data);

//                loginData();
            }
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

        dp.setImageBitmap(thumbnail);
    }
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dp.setImageBitmap(bm);
    }
}
