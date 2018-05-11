package com.example.bouba.cowfarming;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.Toast;

public class Splashscreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Bundle extras = getIntent().getExtras();
                String s="";
                if(extras != null)
                     s = extras.getString("class");
                Intent mainIntent = null;
                Class<?> classType = null;
                s = "com.example.bouba.cowfarming."+s;
                try {
                    classType = Class.forName(s);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                mainIntent = new Intent(Splashscreen.this, classType);
                Splashscreen.this.startActivity(mainIntent);
                Splashscreen.this.finish();
                Splashscreen.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        }, 1000);
    }
}