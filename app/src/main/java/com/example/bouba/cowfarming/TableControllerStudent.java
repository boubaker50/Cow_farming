package com.example.bouba.cowfarming;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bouba.cowfarming.Model.cattles_m;
import com.example.bouba.cowfarming.Model.cows_m;
import com.example.bouba.cowfarming.Model.milk_m;

import java.util.ArrayList;
import java.util.List;

public class TableControllerStudent extends DatabaseHandler {

    public TableControllerStudent(Context context) {
        super(context);
    }
    public boolean create(milk_m objectDays) {

        ContentValues values = new ContentValues();
        values.put("Bon", objectDays.getBon_m());
        values.put("Date", objectDays.getDate_m());
        values.put("Morning", objectDays.getMorning_m());
        values.put("Evening", objectDays.getEvening_m());
        values.put("Total", objectDays.getTotal_m());
        values.put("Earned", objectDays.getEarned_m());
        values.put("Nb_cow", objectDays.getNb_cow_m());
        values.put("Average", objectDays.getAverage_m());
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("Today_rec", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    public boolean create_cattle(cattles_m ObjectCattle) {

        ContentValues values = new ContentValues();
        values.put("Mat", ObjectCattle.getMat_ca());
        values.put("Date_nais", ObjectCattle.getBirth_ca());
        values.put("Gender", ObjectCattle.getGender_ca());
        values.put("Pic", ObjectCattle.getPic_ca());
        values.put("Note", ObjectCattle.getNote_ca());
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("Cattles", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    public boolean create_cow(cows_m ObjectCow) {

        ContentValues values = new ContentValues();
        values.put("Mat", ObjectCow.getMat_c());
        values.put("Date_nais", ObjectCow.getBirth_c());
        values.put("Gender", ObjectCow.getGender_c());
        values.put("Pic", ObjectCow.getPic_c());
        values.put("Note", ObjectCow.getNote_c());
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("Cows", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM Today_rec";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }
    public List<milk_m> read() {

        List<milk_m> recordsList = new ArrayList<milk_m>();

        String sql = "SELECT * FROM Today_rec ORDER BY Date DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String Date = cursor.getString(cursor.getColumnIndex("Date"));
                String bon = cursor.getString(cursor.getColumnIndex("Bon"));
                String mor = cursor.getString(cursor.getColumnIndex("Morning"));
                String eve= cursor.getString(cursor.getColumnIndex("Evening"));
                String tot = cursor.getString(cursor.getColumnIndex("Total"));
                String earn= cursor.getString(cursor.getColumnIndex("Earned"));
                String nb_cow= cursor.getString(cursor.getColumnIndex("Nb_cow"));
                String avg = cursor.getString(cursor.getColumnIndex("Average"));
                milk_m object_days = new milk_m();
                object_days.setDate_m(Date);
                object_days.setBon_m(bon);
                object_days.setMorning_m(mor);
                object_days.setEvening_m(eve);
                object_days.setTotal_m(tot);
                object_days.setEarned_m(earn);
                object_days.setNb_cow_m(nb_cow);
                object_days.setAverage_m(avg);
                recordsList.add(object_days);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }
    public List<cows_m> read_cow() {

        List<cows_m> recordsList = new ArrayList<cows_m>();
        String sql = "SELECT * FROM Cows ORDER BY Date_nais DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String mat = cursor.getString(cursor.getColumnIndex("Mat"));
                int pic = cursor.getInt(cursor.getColumnIndex("Pic"));
                String birth = cursor.getString(cursor.getColumnIndex("Date_nais"));
                String note = cursor.getString(cursor.getColumnIndex("Note"));
                cows_m object_cow = new cows_m();
                object_cow.setMat_c(mat);
                object_cow.setPic_c(pic);
                object_cow.setBirth_c(birth);
                object_cow.setNote_c(note);
                recordsList.add(object_cow);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recordsList;
    }
    public List<cattles_m> read_cattle() {
        List<cattles_m> recordsList = new ArrayList<cattles_m>();
        String sql = "SELECT * FROM Cattles ORDER BY Date_nais DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String mat = cursor.getString(cursor.getColumnIndex("Mat"));
                int pic = cursor.getInt(cursor.getColumnIndex("Pic"));
                String birth = cursor.getString(cursor.getColumnIndex("Date_nais"));
                String note = cursor.getString(cursor.getColumnIndex("Note"));
                cattles_m object_cattle = new cattles_m();
                object_cattle.setMat_ca(mat);
                object_cattle.setPic_ca(pic);
                object_cattle.setBirth_ca(birth);
                object_cattle.setNote_ca(note);
                recordsList.add(object_cattle);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recordsList;
    }
    public boolean Delete_cow(String mat){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.delete("Cows", "Mat='"+mat+"'", null)> 0;
        db.close();
        return createSuccessful;
    }
    public boolean Delete_cattles(String mat){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.delete("Cattles", "Mat='"+mat+"'", null)> 0;
        db.close();
        return createSuccessful;
    }

}