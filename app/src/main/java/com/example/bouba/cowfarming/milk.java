package com.example.bouba.cowfarming;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bouba.cowfarming.Model.cattles_m;
import com.example.bouba.cowfarming.Model.milk_m;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class milk extends AppCompatActivity {
    Bundle i;
    DatabaseReference databaseMilk;
    SharedPreferences muser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        i = getIntent().getExtras();
        databaseMilk = FirebaseDatabase.getInstance().getReference("Milk");
        setContentView(R.layout.activity_milk);
        TextView text = (TextView)findViewById(R.id.textView5);
        Button clear = (Button)findViewById(R.id.button6);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final String currentDateandTime = sdf.format(new Date());
        text.setText(currentDateandTime);
        muser = getSharedPreferences("user", 0);
        final EditText mor = (EditText)findViewById(R.id.editText4);
        final EditText eve = (EditText)findViewById(R.id.editText5);
        final SharedPreferences mPrice = getSharedPreferences("price", 0);
        final TextView tot = (TextView)findViewById(R.id.textView13);
        final TextView earned = (TextView)findViewById(R.id.textView14);
        final EditText nb_cow= (EditText)findViewById(R.id.editText6);
        final TextView aver = (TextView)findViewById(R.id.textView15);
        final EditText bon = (EditText)findViewById(R.id.editText3);
        final Button his = (Button)findViewById(R.id.button5);
        final Button save = (Button)findViewById(R.id.button8);
        final int[] x = {0};
        final int[] y = {0};
        final int[] b = {1};
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bon.setText("");
                mor.setText("");
                eve.setText("");
                nb_cow.setText("");
                tot.setText("");
                aver.setText("");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                milk_m  milk = new milk_m();
                milk.setDate_m(currentDateandTime);
                milk.setBon_m(bon.getText().toString());
                milk.setMorning_m(mor.getText().toString());
                milk.setEvening_m(eve.getText().toString());
                milk.setTotal_m(tot.getText().toString());
                milk.setEarned_m(earned.getText().toString());
                milk.setNb_cow_m(nb_cow.getText().toString());
                milk.setAverage_m(aver.getText().toString());
                if ((milk.getBon_m().length()==0)||(milk.getMorning_m().length()==0)||(milk.getEvening_m().length()==0)||(milk.getNb_cow_m().length()==0)){
                    Toast.makeText(milk.this, "You need to fill all the fields", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean createSuccessful = new TableControllerStudent(milk.this).create(milk);
                if(createSuccessful){
                    Toast.makeText(milk.this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
                    String key = FirebaseDatabase.getInstance().getReference("Depenses").push().getKey();
                    Log.e("Milk object", milk.toString());
                    databaseMilk.child(key).setValue(milk);
                    Toast.makeText(milk.this, "Firebase done.", Toast.LENGTH_SHORT).show();
                    bon.setText("");
                    mor.setText("");
                    earned.setText("");
                    eve.setText("");
                    nb_cow.setText("");
                    tot.setText("");
                    aver.setText("");
                }else{
                    Toast.makeText(milk.this, "Unable to save the data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(milk.this, history.class);
                startActivity(intent);
                milk.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        mor.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(mor.getText().length()!=0){
                    x[0] = Integer.parseInt(mor.getText().toString());}
                tot.setText(""+(x[0] + y[0]));
                earned.setText(""+((x[0] + y[0])*Float.parseFloat(mPrice.getString("price", "0"))));
                if(nb_cow.getText().length()!=0){
                    b[0] = Integer.parseInt(nb_cow.getText().toString());
                }
                try {
                    aver.setText(""+(float)(x[0] + y[0])/ (float)(b[0]));
                }catch (Exception e){
                    Toast.makeText(milk.this, "Please try again", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //doSomething();
            }
        });
        eve.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(eve.getText().length()!=0){
                    y[0] = Integer.parseInt(eve.getText().toString());}
                tot.setText(""+(x[0] + y[0]));
                earned.setText(""+((x[0] + y[0])*Float.parseFloat(mPrice.getString("price", "0"))));
                if(nb_cow.getText().length()!=0){
                    b[0] = Integer.parseInt(nb_cow.getText().toString());
                }
                try {
                    aver.setText(""+(float)(x[0] + y[0])/ (float)(b[0]));
                }catch (Exception e){
                    Toast.makeText(milk.this, "Please try again", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //doSomething();
            }
        });
        nb_cow.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(nb_cow.getText().length()!=0){
                    b[0] = Integer.parseInt(nb_cow.getText().toString());
                }
                else{
                    b[0] = 1;
                }try {
                    aver.setText(""+(float)(x[0] + y[0])/ (float)(b[0]));
                }catch (Exception e){
                    Toast.makeText(milk.this, "Please try again", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //doSomething();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        milk.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}