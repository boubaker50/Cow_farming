package com.example.bouba.cowfarming;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.bouba.cowfarming.Model.cows_m;
import com.example.bouba.cowfarming.Model.edit_cow_m;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
public class Adding_cow extends AppCompatActivity {
    DatabaseReference databaseCows;
    Calendar myCalendar = Calendar.getInstance();
    List<cows_m> cows_mList;
    String link = "";
    File folder;
    static ImageView picture;
    static Spinner gender;
    int pic = -1;
    static EditText matric;
    Button save;
    static EditText dob;
    LinearLayout linearLayoutRecords;
    SharedPreferences muser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_cow);
        cows_mList = new ArrayList<>();
        databaseCows = FirebaseDatabase.getInstance().getReference("Cows");
        link = Environment.getExternalStorageDirectory() + File.separator + "cowFarming/cows";
        muser = getSharedPreferences("user", MODE_PRIVATE);
        folder = new File(link);
        if (!folder.exists())
            folder.mkdirs();
        final Button clear = (Button) findViewById(R.id.clear1);
        save = (Button) findViewById(R.id.save1);
        matric = (EditText) findViewById(R.id.matricule1);
        final EditText notes = (EditText) findViewById(R.id.note1);
        gender = (Spinner) findViewById(R.id.spinner1);
        gender.setSelection(0);
        dob = (EditText) findViewById(R.id.dateofbirth1);
        picture = (ImageView) findViewById(R.id.cow_img_v1);
        linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords1);
        databaseCows.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cows_mList.clear();
                for (DataSnapshot cowSnapshot : dataSnapshot.getChildren()) {
                    cows_m cows_m = cowSnapshot.getValue(com.example.bouba.cowfarming.Model.cows_m.class);
                    cows_mList.add(cows_m);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleear();
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matric.getText().toString().length() == 0) {
                    Toast.makeText(Adding_cow.this, "Fill the matricule field.", Toast.LENGTH_LONG).show();
                    return;
                }
                File image = new File(folder, matric.getText().toString() + ".jpg");
                saveimage(image);
            }
        });
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                File f = new File(link + File.separator + matric.getText().toString() + ".jpg");
                if (f.exists() && !f.isDirectory()) {
                    picture.setImageURI(Uri.parse(link + File.separator + matric.getText().toString() + ".jpg"));
                } else {
                    if (gender.getSelectedItemId() == 1)
                        picture.setImageResource(R.drawable.bmale);
                    if (gender.getSelectedItemId() == 2)
                        picture.setImageResource(R.drawable.bfemale);
                }
                picture.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Utility.updateLabel(dob, myCalendar);
            }
        };
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Adding_cow.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final cows_m objectCow = new cows_m();
                objectCow.setBirth_c(dob.getText().toString());
                objectCow.setMat_c(matric.getText().toString());
                objectCow.setUser_c(muser.getString("user", null));
                objectCow.setMat_c(objectCow.getMat_c().replaceAll("/", "----"));
                objectCow.setGender_c(gender.getSelectedItem().toString());
                final File f = new File(link + File.separator + matric.getText().toString() + ".jpg");
                Log.e("Fields", objectCow.getMat_c()+", "+objectCow.getPic_c()+", "+
                        objectCow.getGender_c()+", "+objectCow.getBirth_c());
                if (matric.getText().toString().length() == 0) {
                    Toast.makeText(Adding_cow.this, "Matricule field can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (dob.getText().toString().length() == 0) {
                    Toast.makeText(Adding_cow.this, "Date of birth field can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (gender.getSelectedItem().toString().equals("Gender")) {
                    Toast.makeText(Adding_cow.this, "Gender field need to be selected.", Toast.LENGTH_LONG).show();
                    return;
                }
                if ((objectCow.getGender_c().equals("Male")) && (!f.exists()) && (!f.isDirectory())) {
                    objectCow.setPic_c(R.drawable.bmale);
                    pic = R.drawable.bmale;
                } else if ((objectCow.getGender_c().equals("Female")) && (!f.exists()) && (!f.isDirectory())) {
                    objectCow.setPic_c(R.drawable.bfemale);
                    pic = R.drawable.bfemale;
                } else {
                    objectCow.setPic_c(0);
                    pic = 0;
                }
                objectCow.setNote_c(notes.getText().toString());
                edit_cow_m edit_cow_m = new edit_cow_m(objectCow.getId_c(), "", "", "0");
                objectCow.setEdit_cow_m(edit_cow_m);
                boolean createSuccessful = false;
                String a = Utility.executeCmd();
                Log.e("length", ""+a.length());
                if ((a .length() == 0)&&(objectCow.getBirth_c() != null)){
                    createSuccessful = new TableControllerStudent(Adding_cow.this).create_cow(objectCow);
                    if (createSuccessful)
                        Toast.makeText(Adding_cow.this, "Data saved localy, No internet connection.", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Adding_cow.this, "Unable to save the data.", Toast.LENGTH_LONG).show();
                }
                else {
                    databaseCows.child(objectCow.getMat_c()).setValue(objectCow);
                    Toast.makeText(Adding_cow.this, "Data saved successfully online.", Toast.LENGTH_SHORT).show();
                    cleear();
                    Intent intent = new Intent(Adding_cow.this, cows.class);
                    startActivity(intent);
                    Adding_cow.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });
    }
    public static void cleear() {
        matric.setText("");
        dob.setText("");
        gender.setSelection(0);
        picture.setImageResource(0);
        picture.setVisibility(View.VISIBLE);
    }
    public void saveimage(File image){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uriSavedImage = Uri.fromFile(image);
        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Adding_cow.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}