package com.example.android.requiry;

/**
 * Created by tito on 11/4/17.
 */

public class Domains {
    private String dName;
    private int dNumOfProj;

    public Domains(String dName, int dNumOfProj) {
        this.dName = dName;
        this.dNumOfProj = dNumOfProj;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public int getdNumOfProj() {
        return dNumOfProj;
    }

    public void setdNumOfProj(int dNumOfProj) {
        this.dNumOfProj = dNumOfProj;
    }

    @Override
    public String toString() {
        return "Domains{" +
                "dName='" + dName + '\'' +
                ", dNumOfProj=" + dNumOfProj +
                '}';
    }
}
