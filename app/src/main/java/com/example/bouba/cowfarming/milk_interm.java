package com.example.bouba.cowfarming;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class milk_interm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_milk_interm);
        Button milk_prod = (Button)findViewById(R.id.milk_prod);
        Button deprev = (Button)findViewById(R.id.deprev);
        ImageView about = (ImageView) findViewById(R.id.about);
        ImageView params = (ImageView) findViewById(R.id.params);
        params.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(milk_interm.this, config.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(milk_interm.this)
                        .setTitle("About us")
                        .setIcon(R.drawable.about_us3)
                        .setMessage("Produced by The Phoenix it\n")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                            }
                        }).show();
            }
        });
        milk_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(milk_interm.this, milk.class));
            }
        });
        deprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(milk_interm.this, depense_rev.class));
            }
        });
    }
}
