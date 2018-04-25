package com.hillygeeks.mdpproject.DataClasses;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String userid;
    String email;
    String phone;
    String name;
    String device_id;

    public User(String email, String device_id) {
        this.email = email;
        this.device_id = device_id;
    }

    public User() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", device_id='" + device_id + '\'' +
                '}';
    }
}
