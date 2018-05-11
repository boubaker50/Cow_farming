package com.example.bouba.cowfarming;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class config extends AppCompatActivity {
    private static final String[]paths = {"item 1", "item 2", "item 3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_config);
        getSupportActionBar().setTitle("Configuration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7f8727")));
        final Spinner spinCountry= (Spinner) findViewById(R.id.spinner);
        final Spinner langg = (Spinner) findViewById(R.id.spinner2);
        final EditText price = (EditText) findViewById(R.id.editText);
        final EditText num =   (EditText) findViewById(R.id.editText2);
        final Switch chose = (Switch) findViewById(R.id.switch1);
        final SharedPreferences mPrice = getSharedPreferences("price", 0);
        final SharedPreferences mNumber = getSharedPreferences("number", 0);
        final SharedPreferences mChose = getSharedPreferences("chose", 0);
        final SharedPreferences mTime = getSharedPreferences("time", 0);
        final SharedPreferences mPos = getSharedPreferences("pos", 0);
        Button save = (Button) findViewById(R.id.button7);
        Button log_out = (Button) findViewById(R.id.button8);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.hours_array));

        ArrayAdapter<String> languagee = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.language_option));

        languagee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langg.setAdapter(languagee);
        spinCountry.setAdapter(adapter);
        num.setText(mNumber.getString("number", "0"));
        price.setText(mPrice.getString("price", "0"));
        spinCountry.setSelection(Integer.parseInt(mTime.getString("time", "0")));
        chose.setChecked(Boolean.valueOf(mChose.getString("chose", "0")));
        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                SharedPreferences.Editor mEditor2 = mPos.edit();
                mEditor2.putString("pos", ""+position).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(config.this, Sign_in.class));
                config.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num.length()!=0){
                    SharedPreferences.Editor mEditor2 = mNumber.edit();
                    mEditor2.putString("number", num.getText().toString()).commit();
                }
                if(price.length()!=0){
                    SharedPreferences.Editor mEditor = mPrice.edit();
                    mEditor.putString("price", price.getText().toString()).commit();
                }
                SharedPreferences.Editor mEditor3 = mChose.edit();
                mEditor3.putString("chose", String.valueOf(chose.isChecked())).commit();
                SharedPreferences.Editor mEditor4 = mTime.edit();
                mEditor4.putString("time", ""+spinCountry.getSelectedItemId()).commit();
                String mString_price = mPrice.getString("price", "0");
                String mString_number = mNumber.getString("number", "0");
                String mString_chose = mChose.getString("chose", "0");
                String mString_time = mTime.getString("time", "0");
                Toast.makeText(config.this, "Modification saved", Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(config.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
