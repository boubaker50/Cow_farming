package com.example.bouba.cowfarming.Model;

/**
 * Created by bouba on 22-Apr-18.
 */

public class milk_m {
    String id_m, user;
    String date_m, bon_m, morning_m, evening_m, total_m, earned_m, nb_cow_m, average_m;
    public milk_m(){

    }

    public milk_m(String id_m, String date, String bon, String mor, String eve, String tot, String earn, String nb_cow, String avg, String user) {
        this.id_m = id_m;
        this.date_m = date;
        this.bon_m = bon;
        this.morning_m = mor;
        this.evening_m = eve;
        this.total_m = tot;
        this.earned_m = earn;
        this.nb_cow_m = nb_cow;
        this.average_m = avg;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId_m() {
        return id_m;
    }

    public void setId_m(String id_m) {
        this.id_m = id_m;
    }

    public String getDate() {
        return date_m;
    }

    public void setDate(String date) {
        this.date_m = date;
    }

    public String getBon() {
        return bon_m;
    }

    public void setBon(String bon) {
        this.bon_m = bon;
    }

    public String getMor() {
        return morning_m;
    }

    public void setMor(String mor) {
        this.morning_m = mor;
    }

    public String getEve() {
        return evening_m;
    }

    public void setEve(String eve) {
        this.evening_m = eve;
    }

    public String getTot() {
        return total_m;
    }

    public void setTot(String tot) {
        this.total_m = tot;
    }

    public String getEarn() {
        return earned_m;
    }

    public void setEarn(String earn) {
        this.earned_m = earn;
    }

    public String getNb_cow() {
        return nb_cow_m;
    }

    public void setNb_cow(String nb_cow) {
        this.nb_cow_m = nb_cow;
    }

    public String getAvg() {
        return average_m;
    }

    public void setAvg(String avg) {
        this.average_m = avg;
    }

}
