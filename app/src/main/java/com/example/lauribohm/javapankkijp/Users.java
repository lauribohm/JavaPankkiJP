package com.example.lauribohm.javapankkijp;

public class Users {

    private String userName, password, name, address, city;

    public Users (String u, String p, String n, String a, String c){

        userName = u;
        password = p;
        name = n;
        address = a;
        city = c;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
