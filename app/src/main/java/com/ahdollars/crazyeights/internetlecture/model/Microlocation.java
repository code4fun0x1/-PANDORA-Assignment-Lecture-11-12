package com.ahdollars.crazyeights.internetlecture.model;

/**
 * Created by Shashank on 03-10-2016.
 */

public class Microlocation {

    public String name;
    public int id;

    public Microlocation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
