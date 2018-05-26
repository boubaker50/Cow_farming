package com.example.bouba.cowfarming;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bouba.cowfarming.Model.cattles_m;
import com.example.bouba.cowfarming.Model.cows_m;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class cattles extends AppCompatActivity {
    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridViewAdapter customGridAdapter;
    LinearLayout linearLayoutRecords;
    EditText dob;
    File folder;
    String link = "";
    ImageView adding_b;
    DatabaseReference databaseCattles;
    List<cattles_m> cattles_mList;
    SharedPreferences muser;
    @Override
    protected void onStart() {
        super.onStart();
        databaseCattles = FirebaseDatabase.getInstance().getReference("Cattles");
        databaseCattles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gridView.setAdapter(null);
                cattles_mList.clear();
                String user = muser.getString("user", null);
                for (DataSnapshot cowSnapshot : dataSnapshot.getChildren()) {
                    cattles_m cows_m = cowSnapshot.getValue(com.example.bouba.cowfarming.Model.cattles_m.class);
                    cows_m.setMat_ca(cows_m.getMat_ca().replaceAll("----", "/"));
                    try {
                        Log.e("matricule", ""+cows_m.getMat_ca());
                        cows_m.setMat_ca(cows_m.getMat_ca().replaceAll(cows_m.getMat_ca().substring(2, cows_m.getMat_ca().indexOf("/")-2), "..."));
                    }
                    catch (Exception e){
                        Log.e("Exception", ""+e);
                    }
                    if(cows_m.getUser_ca().equals(user))
                        cattles_mList.add(cows_m);
                }
                read(cattles_mList);
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
        setContentView(R.layout.activity_cattles);
        muser = getSharedPreferences("user", MODE_PRIVATE);
        cattles_mList = new ArrayList<>();
        final List<cattles_m> cow= new TableControllerStudent(this).read_cattle();
        databaseCattles = FirebaseDatabase.getInstance().getReference("Cattles");
        link = Environment.getExternalStorageDirectory()+File.separator+"cowFarming/cattles";
        folder = new File(link);
        if (!folder.exists())
            folder.mkdirs();
        adding_b = (ImageView)findViewById(R.id.adding_c);
        linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        gridView = (GridView) findViewById(R.id.gridView1);
        adding_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cattles.this, Adding_cattle.class);
                startActivity(intent);
                cattles.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        if ((cow.size()>0)&&(Utility.executeCmd().length()>0)){
            int i = 0;
            for (cattles_m obj : cow) {
                i++;
                databaseCattles.child(obj.getMat_ca()).setValue(obj);
                new TableControllerStudent(this).Delete_cattles(obj.getMat_ca());
            }
            Toast.makeText(this, "Data synced succesfully ", Toast.LENGTH_LONG).show();
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            }
        });
    }
    public void read(List<cattles_m> c){
        if (c.size() > 0) {
            for (cattles_m obj : c) {
                String mat = obj.getMat_ca();
                String birth = obj.getBirth_ca();
                int pic = obj.getPic_ca();
                String gender = obj.getGender_ca();
                String url = link+obj.getMat_ca()+".jpg";
                String note = obj.getNote_ca();
                gridArray.add(new Item(mat, birth, pic, url, note, gender, "", ""));
                customGridAdapter = new CustomGridViewAdapter(this, R.layout.cattle_view, gridArray);
                gridView.setAdapter(customGridAdapter);
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        cattles.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}