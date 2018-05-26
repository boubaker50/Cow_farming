package com.example.bouba.cowfarming.Model;

public class depense_m {
    String id_d, details, value;
    public depense_m(){

    }
    public depense_m(String id_d, String details, String value) {
        this.id_d = id_d;
        this.details = details;
        this.value = value;
    }

    public String getId_d() {
        return id_d;
    }

    public void setId_d(String id_d) {
        this.id_d = id_d;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
