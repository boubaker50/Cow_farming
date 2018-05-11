package com.example.bouba.cowfarming.Model;

/**
 * Created by bouba on 22-Apr-18.
 */

public class cattles_m {
    int pic_ca;
    String mat_ca, birth_ca, gender_ca, note_ca, user_ca;
    public cattles_m(){

    }

    public cattles_m(int pic_ca, String mat_ca, String birth_ca, String gender_ca, String note_ca, String user_ca) {
        this.pic_ca = pic_ca;
        this.mat_ca = mat_ca;
        this.birth_ca = birth_ca;
        this.gender_ca = gender_ca;
        this.note_ca = note_ca;
        this.user_ca = user_ca;
    }

    public String getUser_ca() {
        return user_ca;
    }

    public void setUser_ca(String user_ca) {
        this.user_ca = user_ca;
    }

    public int getPic_ca() {
        return pic_ca;
    }

    public void setPic_ca(int pic_ca) {
        this.pic_ca = pic_ca;
    }

    public String getMat_ca() {
        return mat_ca;
    }

    public void setMat_ca(String mat_ca) {
        this.mat_ca = mat_ca;
    }

    public String getBirth_ca() {
        return birth_ca;
    }

    public void setBirth_ca(String birth_ca) {
        this.birth_ca = birth_ca;
    }

    public String getGender_ca() {
        return gender_ca;
    }

    public void setGender_ca(String gender_ca) {
        this.gender_ca = gender_ca;
    }

    public String getNote_ca() {
        return note_ca;
    }

    public void setNote_ca(String note_ca) {
        this.note_ca = note_ca;
    }
}