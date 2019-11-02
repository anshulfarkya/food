package com.example.anshul.food;

/**
 * Created by anshul on 04-Jun-18.
 */

public class signindata {
    String email;
    String password;
    String type;
    String area;
    String token;
    String key;

    public signindata()
    {

    }

    public signindata(String email, String password, String type,String area,String token) {
        this.email = email;
        this.password = password;
        this.type = type;
        this.area=area;
        this.token=token;
        //this.key=key;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getArea() {
        return area;
    }

    public String getToken() {
        return token;
    }

    /*public String getKey() {
        return key;
    }*/
}

