package com.example.bouba.cowfarming;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        final Button milk = (Button)findViewById(R.id.button3);
        Button cows = (Button)findViewById(R.id.button4);
        Button cattles = (Button)findViewById(R.id.button);
        Button water = (Button)findViewById(R.id.button2);
        final SharedPreferences mNumber = getSharedPreferences("number", 0);
        final SharedPreferences mPos = getSharedPreferences("pos", 0);
        final SharedPreferences mChose = getSharedPreferences("chose", 0);
        final ImageView conf = (ImageView) findViewById(R.id.imageView);
        ImageView about_us = (ImageView) findViewById(R.id.imageView3);
        if(mChose.getString("chose", "0")=="true") {
            Calendar cal = Calendar.getInstance();
            cal.set(cal.HOUR_OF_DAY, Integer.parseInt(mPos.getString("pos", "0")));
            cal.set(cal.MINUTE, 30);
            cal.set(cal.SECOND, 0);
            Intent intent = new Intent(getApplicationContext(), Notification_receiver.class);
            PendingIntent pend = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarm.setRepeating(alarm.RTC_WAKEUP, cal.getTimeInMillis(), alarm.INTERVAL_DAY, pend);
        }
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, milk_interm.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        cows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Splashscreen.class);
                intent.putExtra("class", "cows");
                startActivity(intent);
                MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        cattles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Splashscreen.class);
                intent.putExtra("class", "cattles");
                startActivity(intent);
                MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        water.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LinearLayout layout = new LinearLayout(MainActivity.this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout layout2 = new LinearLayout(MainActivity.this);
            layout2.setOrientation(LinearLayout.HORIZONTAL);
            layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout layout_all = new LinearLayout(MainActivity.this);
            layout_all.setOrientation(LinearLayout.VERTICAL);
            layout_all.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout_all.setPadding(0, 30, 0, 0);
            layout2.setPadding(0, 30, 0, 0);
            final TextView text = new TextView(MainActivity.this);
            final Spinner spin = new Spinner(MainActivity.this);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_spinner_item, getResources()
                    .getStringArray(R.array.hours_array));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(adapter);
            text.setText("BEGIN:    ");
            text.setTextColor(Color.BLACK);
            text.setTextSize(15);
            text.setPadding(200, 0, 0, 0);
            layout.addView(text);
            layout.addView(spin);
            final TextView text2 = new TextView(MainActivity.this);
            final Spinner spin2 = new Spinner(MainActivity.this);
            spin2.setAdapter(adapter);
            text2.setText("STOP:      ");
            text2.setTextColor(Color.BLACK);
            text2.setTextSize(15);
            text2.setPadding(200, 0, 0, 0);
            layout2.addView(text2);
            layout2.addView(spin2);
            layout_all.addView(layout);
            layout_all.addView(layout2);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
            spin2.setSelection(spin.getSelectedItemPosition()+1);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    spin2.setSelection(spin.getSelectedItemPosition()+1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Confirmation")
                    .setIcon(R.drawable.confirm)
                    .setView(layout_all)
                    .setMessage("\nConfigure the watering time.")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            dialoginterface.cancel();
                            Toast.makeText(MainActivity.this, "You canceled the action", Toast.LENGTH_LONG).show();
                        }})
                    .setNeutralButton("Emergency STOP", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Emergency STOP", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            try {
                                sendSMS(mNumber.getString("number", "0"), "Hi, You got a message??!!");

                            }catch (Exception e){
                                Toast.makeText(MainActivity.this, "Error while sending the message"+e, Toast.LENGTH_LONG).show();
                            }

                        }
                    }).show();
        }
    });
     conf.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, config.class);
            startActivity(intent);
            MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
         }
     });
     about_us.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             new AlertDialog.Builder(MainActivity.this)
                     .setTitle("About us")
                     .setIcon(R.drawable.about_us3)
                     .setMessage("Produced by The Phoenix it\n")
                     .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialoginterface, int i) {
                         }
                     }).show();
//             Intent i = new Intent(MainActivity.this, Sign_in.class);
//             startActivity(i);
         }
     });
    }
        private void sendSMS(String phoneNumber, String message) {
        if((Integer.parseInt(phoneNumber)< 10000000)){
            Toast.makeText(MainActivity.this, "Check your saved number from the settings", Toast.LENGTH_LONG).show();
        }
        else {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(MainActivity.this, "Message delevered to the motor", Toast.LENGTH_LONG).show();
        }
        }
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
