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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bouba.cowfarming.Model.cattles_m;
import com.example.bouba.cowfarming.Model.cows_m;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static com.example.bouba.cowfarming.Adding_cow.*;

public class Adding_cattle extends AppCompatActivity {
    DatabaseReference databaseCattles;
    Calendar myCalendar = Calendar.getInstance();
    List<cattles_m> cattle_mList;
    String link = "";
    File folder;
    ImageView picture;
    Spinner gender;
    int pic = -1;
    EditText matric;
    Button save;
    EditText dob;
    LinearLayout linearLayoutRecords;
    SharedPreferences muser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_adding_cattle);
        cattle_mList = new ArrayList<>();
        databaseCattles = FirebaseDatabase.getInstance().getReference("Cattles");
        link = Environment.getExternalStorageDirectory() + File.separator + "cowFarming/cattles";
        muser = getSharedPreferences("user", MODE_PRIVATE);
        folder = new File(link);
        if (!folder.exists())
            folder.mkdirs();
        final Button clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);
        matric = (EditText) findViewById(R.id.matricule);
        final EditText notes = (EditText) findViewById(R.id.note);
        gender = (Spinner) findViewById(R.id.spinner);
        gender.setSelection(0);
        dob = (EditText) findViewById(R.id.dateofbirth);
        picture = (ImageView) findViewById(R.id.cow_img_v);
        linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        databaseCattles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cattle_mList.clear();
                for (DataSnapshot cowSnapshot : dataSnapshot.getChildren()) {
                    cattles_m cows_m = cowSnapshot.getValue(com.example.bouba.cowfarming.Model.cattles_m.class);
                    cattle_mList.add(cows_m);
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
                    Toast.makeText(Adding_cattle.this, "Fill the matricule field.", Toast.LENGTH_LONG).show();
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
                        picture.setImageResource(R.drawable.male);
                    if (gender.getSelectedItemId() == 2)
                        picture.setImageResource(R.drawable.female);
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
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(dob, myCalendar);
            }
        };
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Adding_cattle.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cattles_m objectCow = new cattles_m();
                objectCow.setUser_ca(muser.getString("user", "0"));
                objectCow.setBirth_ca(dob.getText().toString());
                objectCow.setMat_ca(matric.getText().toString());
                objectCow.setUser_ca(muser.getString("user", null));
                objectCow.setMat_ca(objectCow.getMat_ca().replaceAll("/", "----"));
                objectCow.setGender_ca(gender.getSelectedItem().toString());
                final File f = new File(link + File.separator + matric.getText().toString() + ".jpg");
                Log.e("Fields", objectCow.getMat_ca()+", "+objectCow.getPic_ca()+", "+
                        objectCow.getGender_ca()+", "+objectCow.getBirth_ca());
                if (matric.getText().toString().length() == 0) {
                    Toast.makeText(Adding_cattle.this, "Matricule field can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if ((objectCow.getGender_ca().equals("Male")) && (!f.exists()) && (!f.isDirectory())) {
                    objectCow.setPic_ca(R.drawable.male);
                    pic = R.drawable.male;
                } else if ((objectCow.getGender_ca().equals("Female")) && (!f.exists()) && (!f.isDirectory())) {
                    objectCow.setPic_ca(R.drawable.female);
                    pic = R.drawable.female;
                } else {
                    objectCow.setPic_ca(0);
                    pic = 0;
                }
                objectCow.setNote_ca(notes.getText().toString());
                boolean createSuccessful = false;
                String a = executeCmd();
                Log.e("length", ""+a.length());
                if ((a .length() == 0)&&(objectCow.getBirth_ca() != null)){
                    createSuccessful = new TableControllerStudent(Adding_cattle.this).create_cattle(objectCow);
                    if (createSuccessful)
                        Toast.makeText(Adding_cattle.this, "Data saved localy, No internet connection.", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Adding_cattle.this, "Unable to save the data.", Toast.LENGTH_LONG).show();
                }
                else {
                    databaseCattles.child(objectCow.getMat_ca()).setValue(objectCow);
                    Toast.makeText(Adding_cattle.this, "Data saved successfully online.", Toast.LENGTH_SHORT).show();
                    Adding_cow.cleear();
                    Intent intent = new Intent(Adding_cattle.this, cattles.class);
                    startActivity(intent);
                    Adding_cattle.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });
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
        Adding_cattle.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}