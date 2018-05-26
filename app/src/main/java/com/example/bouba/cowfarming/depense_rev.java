package com.example.bouba.cowfarming;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bouba.cowfarming.Model.cattles_m;
import com.example.bouba.cowfarming.Model.cows_m;
import com.example.bouba.cowfarming.Model.depense_m;
import com.example.bouba.cowfarming.Model.milk_m;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class depense_rev extends AppCompatActivity {
    DatabaseReference databasedepenses;
    ImageView plus;
    ListView lv = null;
    SimpleCursorAdapter adapter;
    List<depense_m> depense_mList = new ArrayList<>();
    SharedPreferences muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_depense_rev);
        databasedepenses = FirebaseDatabase.getInstance().getReference("Depenses");
        plus =  (ImageView)findViewById(R.id.plus);
        lv = (ListView) findViewById(R.id.ListViewdep);
        muser =  getBaseContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = muser.edit();
        editor.putInt("id_dep", muser.getInt("id_dep", 0)+1);
        editor.commit();
        TextView date_text = (TextView)findViewById(R.id.date);
        DateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        Date date = new Date();
        date_text.setText(dateFormat.format(date));
        databasedepenses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lv.setAdapter(null);
                depense_mList.clear();
                SharedPreferences.Editor editor = muser.edit();
                editor.putString("user",  muser.getString("user", ""));
                editor.commit();
                for (DataSnapshot milkSnapshot : dataSnapshot.getChildren()) {
                    depense_m depense_m = milkSnapshot.getValue(com.example.bouba.cowfarming.Model.depense_m.class);
                    String verif = muser.getString("user", "");
                    if (depense_m.getId_d().contains(verif.trim()))
                        depense_mList.add(depense_m);
                }
                get_data(depense_mList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialog alertDialoge = new ViewDialog();
                alertDialoge.showDialog(depense_rev.this, "Adding new depense/revenue");
            }
        });
    }
    public class ViewDialog {

        public void showDialog(Activity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogboxx);
            TextView text = (TextView) dialog.findViewById(R.id.textView25);
            text.setText(msg);
            String[] arraySpinner = new String[] {
                    "Select", "+ Revenue", "- Depenses"
            };
            Button okButton = (Button) dialog.findViewById(R.id.button10);
            Button cancleButton = (Button) dialog.findViewById(R.id.button9);
            final Spinner spinner = (Spinner)dialog.findViewById(R.id.spinner3);
            final EditText detail = (EditText) dialog.findViewById(R.id.textView26);
            final EditText value = (EditText) dialog.findViewById(R.id.textView27);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(depense_rev.this,
                    android.R.layout.simple_spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    depense_m depense_m = new depense_m();
                    if (spinner.getSelectedItem().toString().contains("+"))
                        depense_m.setDetails("+ "+detail.getText().toString());
                    else if(spinner.getSelectedItem().toString().contains("-"))
                        depense_m.setDetails("- "+detail.getText().toString());
                    else{
                        Toast.makeText(depense_rev.this, "You should select from the spinner", Toast.LENGTH_LONG).show();
                        return;
                    }
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String key = database.getReference("Depenses").push().getKey();
                    depense_m.setId_d(key+muser.getString("user", ""));
                    depense_m.setValue(value.getText().toString());
                    databasedepenses.child(key).setValue(depense_m);
                    Toast.makeText(depense_rev.this, "Data saved successfully online.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            cancleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
    public void get_data(List<depense_m> c){
        int  i = c.size();
        String[] columns = new String[] { "_id", "col1", "col2"};
        MatrixCursor matrixCursor= new MatrixCursor(columns);
        startManagingCursor(matrixCursor);
        String[] from;
        int[] to;
        from = new String[] {"col1", "col2"};
        to = new int[] { R.id.textViewCol1, R.id.textViewCol2};
        matrixCursor.addRow(new Object[] { 1,"Details", "Value"});
        adapter = new SimpleCursorAdapter(depense_rev.this, R.layout.row_2, matrixCursor, from, to, 0);
        int somme=0;
        if (i>0){
            int j;
            for(j=2; j<i+2; j++) {
                if(c.get(j-2).getDetails().charAt(0)=='+')
                    somme+=Integer.parseInt(c.get(j-2).getValue());
                else if(c.get(j-2).getDetails().charAt(0)=='-')
                    somme-=Integer.parseInt(c.get(j-2).getValue());
                matrixCursor.addRow(new Object[]{j,  c.get(j-2).getDetails().substring(2), c.get(j-2).getDetails().substring(0, 1)+" "+c.get(j-2).getValue()});
                adapter = new SimpleCursorAdapter(depense_rev.this, R.layout.row_2, matrixCursor, from, to, 0);
                }
            matrixCursor.addRow(new Object[] { j+1, "", ""});
            matrixCursor.addRow(new Object[] { j+2, "Sum", somme+" €"});
            adapter = new SimpleCursorAdapter(depense_rev.this, R.layout.row_2, matrixCursor, from, to, 0);
            lv.setAdapter(adapter);
        }
    }
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        depense_rev.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
