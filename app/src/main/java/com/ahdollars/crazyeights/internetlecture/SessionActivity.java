package com.ahdollars.crazyeights.internetlecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahdollars.crazyeights.internetlecture.api.SessionApi;
import com.ahdollars.crazyeights.internetlecture.model.EventSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SessionActivity extends AppCompatActivity {

    public int id;
    public static final String TAG="RETRO";
    public ArrayList<EventSession> session=new ArrayList<>();
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        rv=(RecyclerView)findViewById(R.id.session_holder);
        rv.setLayoutManager(new LinearLayoutManager(this));
        id=getIntent().getIntExtra("id",-1);


        Retrofit rFit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://open-event-dev.herokuapp.com/api/v2/")
                .build();
        final SessionApi sessionapi=rFit.create(SessionApi.class);  //set up the interface

        sessionapi.getSessionList(id).enqueue(new Callback<ArrayList<EventSession>>() {
            @Override
            public void onResponse(Call<ArrayList<EventSession>> call, Response<ArrayList<EventSession>> response) {
                //Toast.makeText(SessionActivity.this,response.body().get(0).Microlocation.getName()+"",Toast.LENGTH_LONG).show();
                session=response.body();
                rv.setAdapter(new SessionAdapter());
            }
            @Override
            public void onFailure(Call<ArrayList<EventSession>> call, Throwable t) {

            }
        });




    }


    public class SessionHolder extends RecyclerView.ViewHolder{

         public TextView title,longabstract,starttime,endtime,venue,id;


        public SessionHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tvTitle);
            longabstract=(TextView)itemView.findViewById(R.id.long_abstract);
            starttime=(TextView)itemView.findViewById(R.id.starttime);
            endtime=(TextView)itemView.findViewById(R.id.stoptime);
            venue=(TextView)itemView.findViewById(R.id.venue);
            id=(TextView)itemView.findViewById(R.id.tvId);
        }
    }


    public class SessionAdapter extends RecyclerView.Adapter<SessionHolder>{


        @Override
        public SessionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=getLayoutInflater().inflate(R.layout.session_card,parent,false);
            SessionHolder holder=new SessionHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(SessionHolder holder, int position) {
            EventSession s=session.get(position);
            holder.title.setText(s.getTitle());
            holder.longabstract.setText(s.getLong_abstract());
            holder.starttime.setText(s.getStart_time());
            holder.endtime.setText(s.getEnd_time());
            holder.venue.setText(s.getMicrolocation().name);
            holder.id.setText(s.getId()+"");

        }

        @Override
        public int getItemCount() {
            return session.size();
        }
    }


}
