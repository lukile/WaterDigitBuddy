package com.example.lukile.pokeswim.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class User {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Date age;
    private String gender;
    private String deviceId;

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getLastname() {
        return lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setAge(String age) {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Instant instant = Instant.parse(age) ;

        Date dateAge = Date.from(instant) ;

            //Date dateAge = format.parse(age);
        Log.e("age convert: ", String.valueOf(dateAge));
        this.age = dateAge;
    }
    public Date getAge() {
        return age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getDeviceId() { return deviceId; }

}
