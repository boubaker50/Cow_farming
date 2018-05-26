package com.example.bouba.cowfarming;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {
    public static void updateLabel(EditText dob, Calendar myCalendar) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(myCalendar.getTime()));
    }
    public static String executeCmd(){
        String res = "";
        try {
            Process p;
            p= Runtime.getRuntime().exec("ping -c 1 -w 1 8.8.8.8");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            while ((s = stdInput.readLine()) != null) {
                res += s + "\n";
            }
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public static String manage_date(String date, String s){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (s.equalsIgnoreCase("add"))
            c.add(Calendar.DATE, 280);
        else
            c.add(Calendar.DATE, -70);
        return sdf.format(c.getTime());
    }
}
