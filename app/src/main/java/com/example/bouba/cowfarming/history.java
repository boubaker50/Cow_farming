package com.example.bouba.cowfarming;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bouba.cowfarming.Model.cows_m;
import com.example.bouba.cowfarming.Model.milk_m;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class history extends AppCompatActivity {
    String[] a= new String[100];
    List<List> list = new ArrayList<>();
    SimpleCursorAdapter adapter;
    EditText filter = null;
    Calendar myCalendar = Calendar.getInstance();
    List<milk_m> milk_mList = null;
    ListView lv = null;
    DatabaseReference databaseMilk;
    SharedPreferences muser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_history);
        lv = (ListView) findViewById(R.id.lv);
        milk_mList = new ArrayList<>();
        muser = getSharedPreferences("user", MODE_PRIVATE);
        filter = (EditText)findViewById(R.id.editText10);
        databaseMilk = FirebaseDatabase.getInstance().getReference("Milk");
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                databaseMilk.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        lv.setAdapter(null);
                        milk_mList.clear();
                        String user = muser.getString("user", null);
                        for (DataSnapshot milkSnapshot : dataSnapshot.getChildren()) {
                            milk_m milk_m = milkSnapshot.getValue(com.example.bouba.cowfarming.Model.milk_m.class);
                            if(milk_m.getUser() == user)
                                milk_mList.add(milk_m);
                        }
                        get_data(milk_mList);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(history.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    public void get_data(List<milk_m> c){
        int  i = c.size();
        String[] columns = new String[] { "_id", "col1", "col2","col3" };
        MatrixCursor matrixCursor= new MatrixCursor(columns);
        startManagingCursor(matrixCursor);
        matrixCursor.addRow(new Object[] { 1,"Date","Total", "Earned" });
        String[] from;
        filter = (EditText) findViewById(R.id.editText10);
        int[] to;
        from = new String[] {"col1", "col2", "col3"};
        to = new int[] { R.id.textViewCol1, R.id.textViewCol2, R.id.textViewCol3};
        final String OLD_FORMAT = "MMMM yyyy";
        final String NEW_FORMAT = "MM/yyyy";

        String oldDateString = filter.getText().toString();
        String newDateString;
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);
        list = readRecords(milk_mList);
        if (i>0){
            int j;
            int somme_mlk=0;
            float somme_ear=0;
            for(j=2; j<i+2; j++) {
                if (list.get(0).get(j-2).toString().contains(newDateString)) {
                matrixCursor.addRow(new Object[]{j, list.get(0).get(j - 2), list.get(1).get(j - 2), list.get(2).get(j - 2)});
                adapter = new SimpleCursorAdapter(history.this, R.layout.row_item, matrixCursor, from, to, 0);
                somme_mlk = somme_mlk + Integer.parseInt((String) list.get(1).get(j - 2));
                somme_ear = somme_ear + Float.parseFloat((String) list.get(2).get(j - 2));
                }
            }
            matrixCursor.addRow(new Object[] { j+1, "", "", ""});
            matrixCursor.addRow(new Object[] { j+2, "Total: ", somme_mlk, somme_ear+" €"});
            adapter = new SimpleCursorAdapter(history.this, R.layout.row_item, matrixCursor, from, to, 0);
            lv.setAdapter(adapter);
        }
    }
    public List<List> readRecords(List<milk_m> c) {
        String ar[] = new String[3];
        List<List> list1 = new ArrayList<>();
        List<String> date = new ArrayList<>();
        List<String> tot = new ArrayList<>();
        List<String> ear = new ArrayList<>();
        LinearLayout linearLayoutRecords = new LinearLayout(this);
        linearLayoutRecords.setOrientation(LinearLayout.VERTICAL);
        linearLayoutRecords.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        linearLayoutRecords.removeAllViews();
        if (c.size() > 0) {
            for (milk_m obj : c) {
                String Date = obj.getDate();
                String Bon = obj.getBon();
                String Morning = obj.getMor();
                String Evening = obj.getEve();
                String Total = obj.getTot();
                String Earned = obj.getEarn();
                String Nb_cow = obj.getNb_cow();
                String Average = obj.getAvg();
                date.add(Date);
                tot.add(Total);
                ear.add(Earned);
            }
        }
        list1.add(date);
        list1.add(tot);
        list1.add(ear);
        return list1;
    }
    private void updateLabel() {
        String myFormat = "MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        filter.setText(sdf.format(myCalendar.getTime()));
    }
}