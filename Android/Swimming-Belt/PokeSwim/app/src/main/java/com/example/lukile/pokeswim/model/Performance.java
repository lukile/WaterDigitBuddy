package com.example.lukile.pokeswim.model;

import android.util.Log;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Performance {
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("speed")
    @Expose
    private float speed;

    private Date startTime;
    private Date endTime;
    private Integer lengthType;
    private String programType;
    private String user;
    private String deviceid;

    public Integer getDistance() {
        return distance;
    }
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Integer getLengthType() { return lengthType; }
    public void setLengthType(Integer lengthType) { this.lengthType = lengthType; }

    public String getProgramType() { return programType; }
    public void setProgramType(String programType) { this.programType = programType; }

    public String getUser() { return user; }
    public void setUser(String userId) { this.user = userId; }

    public String getDeviceid() { return deviceid; }
    public void setDeviceid(String deviceid) { this.deviceid = deviceid; }

    public Date getStartTime() { return startTime; }
    public void setStartTime(String startTime) {
        Instant instant = Instant.parse(startTime) ;

        Date dateStartTime = Date.from(instant) ;

        Log.e("startime convert: ", String.valueOf(dateStartTime));
        this.startTime = dateStartTime;
    }

    public Date getEndTime() { return endTime; }
    public void setEndTime(String endTime) {
        Instant instant = Instant.parse(endTime) ;

        Date dateEndTime = Date.from(instant) ;

        Log.e("endTime convert: ", String.valueOf(dateEndTime));
        this.endTime = dateEndTime;
    }

}
