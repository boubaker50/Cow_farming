package com.example.bouba.cowfarming.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bouba on 22-Apr-18.
 */

public class cows_m{
    String id_c;
    int pic_c;
    String mat_c, birth_c, gender_c, note_c, user_c;
    public cows_m(){

    }

    public cows_m(int pic_c, String mat_c, String birth_c, String gender_c, String note_c, String user_c) {
        this.pic_c = pic_c;
        this.mat_c = mat_c;
        this.birth_c = birth_c;
        this.gender_c = gender_c;
        this.note_c = note_c;
        this.user_c = user_c;
    }

    public String getUser_c() {
        return user_c;
    }

    public void setUser_c(String user_c) {
        this.user_c = user_c;
    }

    public int getPic_c() {
        return pic_c;
    }

    public void setPic_c(int pic_c) {
        this.pic_c = pic_c;
    }

    public String getMat_c() {
        return mat_c;
    }

    public void setMat_c(String mat_c) {
        this.mat_c = mat_c;
    }

    public String getBirth_c() {
        return birth_c;
    }

    public void setBirth_c(String birth_c) {
        this.birth_c = birth_c;
    }

    public String getGender_c() {
        return gender_c;
    }

    public void setGender_c(String gender_c) {
        this.gender_c = gender_c;
    }

    public String getNote_c() {
        return note_c;
    }

    public void setNote_c(String note_c) {
        this.note_c = note_c;
    }

    @Override
    public String toString() {
        return "cows_m{" +
                "id_c='" + id_c + '\'' +
                ", pic_c=" + pic_c +
                ", mat_c='" + mat_c + '\'' +
                ", birth_c='" + birth_c + '\'' +
                ", gender_c='" + gender_c + '\'' +
                ", note_c='" + note_c + '\'' +
                '}';
    }
}
