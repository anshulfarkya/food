package com.example.anshul.food;

/**
 * Created by anshul on 03-Jun-18.
 */

public class User {
    String id;
    String name;
    String number;
    String address;
    String locality;

    public User()
    {

    }

    public User(String id, String name, String number, String address,String locality) {
        this.id=id;
        this.name = name;
        this.number = number;
        this.address = address;
        this.locality=locality;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getLocality(){ return locality;}
}
