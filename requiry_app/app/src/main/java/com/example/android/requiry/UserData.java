package com.example.android.requiry;

/**
 * Created by MAHE on 10-Mar-17.
 */
public class UserData {
    private int uID;
    private String uName;
    private String uNumber;
    private String uEmail;
    private String uUsername;
    private int uWho;
    private String uDesc;

    public UserData(int id, String name, String number, String email, String username, int who, String desc) {
        uID = id;
        uName = name;
        uNumber = number;
        uEmail = email;
        uUsername = username;
        uWho = who;
        uDesc = desc;
    }

    public int getuID() {
        return uID;
    }

    public String getName() {
        return uName;
    }

    public String getNumber() {
        return uNumber;
    }

    public String getEmail() {
        return uEmail;
    }

    public String getUsername() {
        return uUsername;
    }

    public int getWho() {
        return uWho;
    }

    public String getDesc() {
        return uDesc;
    }
}
