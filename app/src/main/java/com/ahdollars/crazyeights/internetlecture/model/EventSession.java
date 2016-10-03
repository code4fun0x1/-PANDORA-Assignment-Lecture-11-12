package com.ahdollars.crazyeights.internetlecture.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shashank on 03-10-2016.
 */

public class EventSession {


    public String start_time;
    public String title;
    public int id;
    public String long_abstract;
    public String end_time;

    @SerializedName("microlocation")
    public Microlocation Microlocation;

    public EventSession(String long_abstract,  String start_time, String title,Microlocation Microlocation,String end_time,int id) {
        this.long_abstract = long_abstract;
        this.Microlocation = Microlocation;
        this.start_time = start_time;
        this.title = title;
        this.end_time=end_time;
        this.id=id;
    }


    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Microlocation getMicrolocation() {
        return Microlocation;
    }

    public void setMicrolocation(Microlocation Microlocation) {
        this.Microlocation = Microlocation;
    }

    public String getLong_abstract() {
        return long_abstract;
    }

    public void setLong_abstract(String long_abstract) {
        this.long_abstract = long_abstract;
    }




    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

