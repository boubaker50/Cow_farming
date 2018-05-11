package com.example.bouba.cowfarming;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bouba.cowfarming.Model.cows_m;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class cows extends AppCompatActivity {
    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<>();
    CustomGridViewAdapter customGridAdapter;
    LinearLayout linearLayoutRecords;
    EditText dob;
    File folder;
    ImageView adding_b;
    DatabaseReference databaseCows;
    String link = Environment.getExternalStorageDirectory() + File.separator + "cowFarming/cows";
    List<cows_m> cows_mList;
    List<cows_m> cow;
    SharedPreferences muser;
    @Override
    protected void onStart() {
        super.onStart();
        databaseCows = FirebaseDatabase.getInstance().getReference("Cows");
        databaseCows.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gridView.setAdapter(null);
                cows_mList.clear();
                String user = muser.getString("user", null);
                for (DataSnapshot cowSnapshot : dataSnapshot.getChildren()) {
                    cows_m cows_m = cowSnapshot.getValue(com.example.bouba.cowfarming.Model.cows_m.class);
                    cows_m.setMat_c(cows_m.getMat_c().replaceAll("----", "/"));
                    if(cows_m.getUser_c().equals(user))
                        cows_mList.add(cows_m);
                }
                read(cows_mList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cows);
        muser = getSharedPreferences("user", MODE_PRIVATE);
        cow = new TableControllerStudent(this).read_cow();
        cows_mList = new ArrayList<>();
        databaseCows = FirebaseDatabase.getInstance().getReference("Cows");
        link = Environment.getExternalStorageDirectory()+File.separator+"cowFarming/cows";
        folder = new File(link);
        if (!folder.exists())
            folder.mkdirs();
        adding_b = (ImageView)findViewById(R.id.adding_c);
        linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        gridView = (GridView) findViewById(R.id.gridView1);
        adding_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.setAdapter(null);
                Intent intent = new Intent(cows.this, Adding_cow.class);
                startActivity(intent);
                cows.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        if ((cow.size()>0)&&(Adding_cow.executeCmd().length()>0)){
            int i = 0;
            for (cows_m obj : cow) {
                i++;
                databaseCows.child(obj.getMat_c()).setValue(obj);
                new TableControllerStudent(this).Delete_cow(obj.getMat_c());
            }
            Toast.makeText(this, "Data synced succesfully ", Toast.LENGTH_LONG).show();
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(cows.this, edit_cow.class);
                cows_m selected = cows_mList.get(position);
                Gson gson = new Gson();
                String myJson = gson.toJson(selected);
                intent.putExtra("myjson", myJson);
                startActivity(intent);
                cows.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
    public void read(List<cows_m> c){
        if (c.size() > 0) {
            for (cows_m obj : c) {
                String mat = obj.getMat_c();
                String birth = obj.getBirth_c();
                String gender = obj.getGender_c();
                int pic = obj.getPic_c();
                String url = link+obj.getMat_c()+".jpg";
                String note = obj.getNote_c();
                gridArray.add(new Item(mat, birth, pic, url, note, gender));
                customGridAdapter = new CustomGridViewAdapter(this, R.layout.cow_view, gridArray);
                gridView.setAdapter(customGridAdapter);
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        cows.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}