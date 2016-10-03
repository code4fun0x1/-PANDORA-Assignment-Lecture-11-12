package com.ahdollars.crazyeights.internetlecture.model;

import java.util.ArrayList;



public class Event {
    public String organiserName;
    public String discription;
    public String topic;
    public String identifier;
    public String schedule;
    public String backgroundurl;
    public String background;
    public String location;
    public int expand=0;
    public int id;
    public boolean has_session_speakers;
    public ArrayList<Social> socialMedia=new ArrayList<>();

    public Event(String backgroundurl, String discription, String identifier, String location, String organiserName, String schedule, ArrayList<Social> socialMedia, String topic,String background,int id,boolean has_session_speakers) {
        this.backgroundurl = backgroundurl;
        this.discription = discription;
        this.identifier = identifier;
        this.location = location;
        this.organiserName = organiserName;
        this.schedule = schedule;
        this.socialMedia = socialMedia;
        this.topic = topic;
        this.background=background;
        this.id=id;
        this.has_session_speakers=has_session_speakers;
    }
}
