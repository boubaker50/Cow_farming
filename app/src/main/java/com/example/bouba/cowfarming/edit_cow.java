package com.example.bouba.cowfarming;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bouba.cowfarming.Model.cows_m;
import com.google.gson.Gson;

public class edit_cow extends AppCompatActivity {
    ImageView cow_im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        cow_im = (ImageView)findViewById(R.id.cow_im);
        setContentView(R.layout.activity_edit_cow);
        Gson gson = new Gson();
        cows_m cow = gson.fromJson(getIntent().getStringExtra("myjson"), cows_m.class);
        Toast.makeText(this, cow.getBirth_c()+" <> "+cow.getGender_c(), Toast.LENGTH_LONG).show();
    }
    public void onBackPressed() {
        Intent i = new Intent(this, cows.class);
        startActivity(i);
        edit_cow.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}