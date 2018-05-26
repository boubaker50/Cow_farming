package com.example.bouba.cowfarming;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bouba.cowfarming.Model.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import java.util.Calendar;

public class edit_cow extends AppCompatActivity {
    ImageView cow_im;
    DatabaseReference databaseCowsEdit;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_cow);
        Button save   = (Button)findViewById(R.id.save);
        Button returrn = (Button)findViewById(R.id.cancel);
        Gson gson = new Gson();
        final cows_m cow = gson.fromJson(getIntent().getStringExtra("myjson"), cows_m.class);
        TextView date_of_birth = (TextView)findViewById(R.id.textView29);
        databaseCowsEdit = FirebaseDatabase.getInstance().getReference("Cows");
        final EditText mating = (EditText)findViewById(R.id.editText8);
        TextView matricule = (TextView)findViewById(R.id.textView28);
        final TextView nb_calv = (TextView)findViewById(R.id.textView30);
        final EditText last_calv = (EditText)findViewById(R.id.editText7);
        Button plus = (Button)findViewById(R.id.button11);
        Button minus = (Button)findViewById(R.id.button12);
        try {
            last_calv.setText(cow.getEdit_cow_m().getDate_calving());
            mating.setText(cow.getEdit_cow_m().getDate_mating());
        }catch (Exception e){
            Log.e("Error", ""+e);
        }
        if (cow.getEdit_cow_m().getNb_calving()==null)
            nb_calv.setText("0");
        else
            nb_calv.setText(""+cow.getEdit_cow_m().getNb_calving());
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nb_calv.setText(""+(Integer.parseInt(nb_calv.getText().toString())+1));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(nb_calv.getText().toString())>0)
                    nb_calv.setText(""+(Integer.parseInt(nb_calv.getText().toString())-1));
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
                Utility.updateLabel(last_calv, myCalendar);

            }
        };
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Utility.updateLabel(mating, myCalendar);

            }
        };
        last_calv.setInputType(InputType.TYPE_NULL);
        mating.setInputType(InputType.TYPE_NULL);
        last_calv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(edit_cow.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        mating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(edit_cow.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        cow_im = (ImageView)findViewById(R.id.cow_im);
        if (cow.getGender_c().equals("Female"))
            cow_im.setImageResource(R.drawable.bfemale);
        else
            cow_im.setImageResource(R.drawable.bmale);
        date_of_birth.setText(cow.getBirth_c());
        matricule.setText(cow.getMat_c());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_cow_m edit_cow_m = new edit_cow_m(cow.getId_c(), last_calv.getText().toString(), mating.getText().toString(), nb_calv.getText().toString());
                cow.setEdit_cow_m(edit_cow_m);
                databaseCowsEdit.child(cow.getMat_c()).setValue(cow);
                Toast.makeText(edit_cow.this, "Data saved successfully online.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(edit_cow.this, cows.class);
                startActivity(i);
                edit_cow.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        returrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(edit_cow.this, cows.class);
                startActivity(i);
                edit_cow.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
    public void onBackPressed() {
        Intent i = new Intent(this, cows.class);
        startActivity(i);
        edit_cow.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}