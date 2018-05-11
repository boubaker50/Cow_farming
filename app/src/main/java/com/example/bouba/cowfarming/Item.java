package com.example.bouba.cowfarming;

/**
 * Created by bouba on 01-Feb-18.
 */

public class Item {
    String matricule;
    String dob;
    int pic;
    String gender;
    String url;
    String note;


    public Item(String matricule, String dob, int pic, String url, String note, String gender) {
        super();
        this.matricule = matricule;
        this.dob = dob;
        this.pic = pic;
        this.url = url;
        this.note = note;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}