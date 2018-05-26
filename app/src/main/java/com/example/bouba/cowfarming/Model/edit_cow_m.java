package com.example.bouba.cowfarming.Model;

public class edit_cow_m {
    String id_cow;
    String date_calving, date_mating, nb_calving;
    public edit_cow_m(){

    }
    public edit_cow_m(String id_cow, String date_calving, String date_mating, String nb_calving) {
        this.id_cow = id_cow;
        this.date_calving = date_calving;
        this.date_mating = date_mating;
        this.nb_calving = nb_calving;
    }

    public String getId_cow() {
        return id_cow;
    }

    public void setId_cow(String id_cow) {
        this.id_cow = id_cow;
    }

    public String getDate_calving() {
        return date_calving;
    }

    public void setDate_calving(String date_calving) {
        this.date_calving = date_calving;
    }

    public String getDate_mating() {
        return date_mating;
    }

    public void setDate_mating(String date_mating) {
        this.date_mating = date_mating;
    }

    public String getNb_calving() {
        return nb_calving;
    }

    public void setNb_calving(String nb_calving) {
        this.nb_calving = nb_calving;
    }

    @Override
    public String toString() {
        return "edit_cow_m{" +
                "id_cow='" + id_cow + '\'' +
                ", date_calving='" + date_calving + '\'' +
                ", date_mating='" + date_mating + '\'' +
                ", nb_calving='" + nb_calving + '\'' +
                '}';
    }
}