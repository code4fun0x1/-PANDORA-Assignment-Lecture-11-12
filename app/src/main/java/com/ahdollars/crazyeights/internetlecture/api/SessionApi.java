package com.ahdollars.crazyeights.internetlecture.api;

import com.ahdollars.crazyeights.internetlecture.model.EventSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Shashank on 03-10-2016.
 */

public interface SessionApi {

    //in this we only get the sessions of a particular id
    //we supplu the ID
    @GET("events/{id}/sessions")
    Call<ArrayList<EventSession>> getSessionList(@Path("id") int id);

}
