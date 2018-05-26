package com.example.bouba.cowfarming.Model;

/**
 * Created by bouba on 22-Apr-18.
 */

public class milk_m {
    String id_m, user;
    String date_m, bon_m, morning_m, evening_m, total_m, earned_m, nb_cow_m, average_m;
    public milk_m(){

    }

    @Override
    public String toString() {
        return "milk_m{" +
                "id_m='" + id_m + '\'' +
                ", user='" + user + '\'' +
                ", date_m='" + date_m + '\'' +
                ", bon_m='" + bon_m + '\'' +
                ", morning_m='" + morning_m + '\'' +
                ", evening_m='" + evening_m + '\'' +
                ", total_m='" + total_m + '\'' +
                ", earned_m='" + earned_m + '\'' +
                ", nb_cow_m='" + nb_cow_m + '\'' +
                ", average_m='" + average_m + '\'' +
                '}';
    }

    public String getId_m() {
        return id_m;
    }

    public void setId_m(String id_m) {
        this.id_m = id_m;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate_m() {
        return date_m;
    }

    public void setDate_m(String date_m) {
        this.date_m = date_m;
    }

    public String getBon_m() {
        return bon_m;
    }

    public void setBon_m(String bon_m) {
        this.bon_m = bon_m;
    }

    public String getMorning_m() {
        return morning_m;
    }

    public void setMorning_m(String morning_m) {
        this.morning_m = morning_m;
    }

    public String getEvening_m() {
        return evening_m;
    }

    public void setEvening_m(String evening_m) {
        this.evening_m = evening_m;
    }

    public String getTotal_m() {
        return total_m;
    }

    public void setTotal_m(String total_m) {
        this.total_m = total_m;
    }

    public String getEarned_m() {
        return earned_m;
    }

    public void setEarned_m(String earned_m) {
        this.earned_m = earned_m;
    }

    public String getNb_cow_m() {
        return nb_cow_m;
    }

    public void setNb_cow_m(String nb_cow_m) {
        this.nb_cow_m = nb_cow_m;
    }

    public String getAverage_m() {
        return average_m;
    }

    public void setAverage_m(String average_m) {
        this.average_m = average_m;
    }

    public milk_m(String id_m, String user, String date_m, String bon_m, String morning_m, String evening_m, String total_m, String earned_m, String nb_cow_m, String average_m) {
        this.id_m = id_m;
        this.user = user;
        this.date_m = date_m;
        this.bon_m = bon_m;
        this.morning_m = morning_m;
        this.evening_m = evening_m;
        this.total_m = total_m;
        this.earned_m = earned_m;
        this.nb_cow_m = nb_cow_m;
        this.average_m = average_m;
    }
}
