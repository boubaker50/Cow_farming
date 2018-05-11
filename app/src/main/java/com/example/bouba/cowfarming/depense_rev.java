package com.example.bouba.cowfarming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class depense_rev extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_depense_rev);
        TextView date_text = (TextView)findViewById(R.id.date);
        DateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        Date date = new Date();
        date_text.setText(dateFormat.format(date));
    }
}
