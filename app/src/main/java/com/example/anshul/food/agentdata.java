package com.example.anshul.food;

/**
 * Created by anshul on 04-Jun-18.
 */

public class agentdata {
    String name;
    String email;
    String password;
    String  number;
    String address;
    String area;

    public agentdata()
    {

    }

    public agentdata(String name, String email, String password, String number, String address, String area) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.number = number;
        this.address = address;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }
}
