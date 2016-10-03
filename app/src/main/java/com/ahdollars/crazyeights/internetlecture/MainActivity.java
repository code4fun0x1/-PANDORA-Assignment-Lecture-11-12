package com.ahdollars.crazyeights.internetlecture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahdollars.crazyeights.internetlecture.model.Event;
import com.ahdollars.crazyeights.internetlecture.model.Social;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="BATMAN1";
    RecyclerView rv;
    public ArrayList<Event> events=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=(RecyclerView)findViewById(R.id.mainbody);

        if(checkInternet()){
            NetFetchTask n=new NetFetchTask(){

                @Override
                protected void onPostExecute(ArrayList<Event> s) {
                    super.onPostExecute(s);
                    events=s;
                  //  Toast.makeText(getApplicationContext(),s.size(),Toast.LENGTH_LONG).show();
                    rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rv.setAdapter(new MyAdapter());

                }
            };
            n.execute("http://open-event-dev.herokuapp.com/api/v2/events");

        }


    }

    public boolean checkInternet(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();
        if(ni!=null && ni.isConnected()){
          //  Log.d(TAG, "checkInternet: typeName "+ni.getTypeName());
          //  Log.d(TAG, "checkInternet: state "+ni.getState());
          //  Log.d(TAG, "checkInternet: extraInfo"+ni.getExtraInfo());
          //  Log.d(TAG, "checkInternet: reason"+ni.getReason());
            return true;
        }


        return false;
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView organiserName,discription,topic,schedule,location;
        ImageView background,fb,gp,twitter,youtube,github,expand;
        LinearLayout ll;
        View v;

        public MyHolder(View itemView) {
            super(itemView);
            v=itemView;
            organiserName=(TextView)itemView.findViewById(R.id.organiser_name);
            discription=(TextView)itemView.findViewById(R.id.discription);
            topic=(TextView)itemView.findViewById(R.id.topic);
            schedule=(TextView)itemView.findViewById(R.id.schedule);
            location=(TextView)itemView.findViewById(R.id.location);
            background=(ImageView)itemView.findViewById(R.id.iv_backgroind_image);
            fb=(ImageView)itemView.findViewById(R.id.facebook);
            gp=(ImageView)itemView.findViewById(R.id.gplus);
            twitter=(ImageView)itemView.findViewById(R.id.twitter);
            youtube=(ImageView)itemView.findViewById(R.id.youtube);
            github=(ImageView)itemView.findViewById(R.id.github);
            expand=(ImageView)itemView.findViewById(R.id.ib_expand);
            ll=(LinearLayout)itemView.findViewById(R.id.body);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements View.OnClickListener{

        ArrayList<Social> socialMedia=new ArrayList<>();

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=getLayoutInflater().inflate(R.layout.event_card,parent,false);
            MyHolder holder=new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, int position) {

            final Event ev=events.get(position);
            socialMedia=ev.socialMedia;
            holder.ll.setVisibility(View.GONE);
            holder.background.setImageBitmap(null);
            if(ev.expand==1){
                holder.ll.setVisibility(View.VISIBLE);
            }
            holder.organiserName.setText(ev.organiserName);
            holder.topic.setText(ev.topic);
            holder.discription.setText(ev.discription);
            holder.location.setText(ev.location);
            holder.schedule.setText(ev.schedule);

            AsyncTask<String,Void,Bitmap> ss=new AsyncTask<String, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... params) {
                    File f=new File(Environment.getExternalStorageDirectory(),params[0]);
                    if(f.exists()){
                        return BitmapFactory.decodeFile(f.getAbsolutePath());
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    holder.background.setImageBitmap(bitmap);
                }
            };
            ss.execute(ev.background);

            holder.expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(int i=0;i<events.size();i++){
                        events.get(i).expand=0;
                    }
                    notifyDataSetChanged();
                    ev.expand=1;
                    holder.ll.setVisibility(View.VISIBLE);
                }
            });

            //for long click listener
            holder.v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    for(int i=0;i<events.size();i++){
                        events.get(i).expand=0;
                    }
                    notifyDataSetChanged();
                    ev.expand=1;
                    holder.ll.setVisibility(View.VISIBLE);
                    return false;
                }
            });

            holder.fb.setOnClickListener(this);
            holder.youtube.setOnClickListener(this);
            holder.twitter.setOnClickListener(this);
            holder.github.setOnClickListener(this);
            holder.gp.setOnClickListener(this);
            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(MainActivity.this, SessionActivity.class);
                    i.putExtra("id",ev.id);
                    if(ev.has_session_speakers)
                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        @Override
        public void onClick(View v) {
         //   Toast.makeText(getApplicationContext(),socialMedia.size()+"",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);


            if(v.getId()==R.id.facebook){

                for(int i=0;i<socialMedia.size();i++){
                    if(socialMedia.get(i).name.equals("Facebook")){

                        intent.setData(Uri.parse(socialMedia.get(i).link));
                        startActivity(intent);

                    }
                }
            }else if(v.getId()==R.id.twitter){
                for(int i=0;i<socialMedia.size();i++){
                    if(socialMedia.get(i).name.equals("Twitter")){

                        intent.setData(Uri.parse(socialMedia.get(i).link));
                        startActivity(intent);

                    }
                }
            }else if(v.getId()==R.id.github){

                for(int i=0;i<socialMedia.size();i++){
                    if(socialMedia.get(i).name.equals("Github")){

                        intent.setData(Uri.parse(socialMedia.get(i).link));
                        startActivity(intent);
                    }
                }

            }else if(v.getId()==R.id.gplus){

                for(int i=0;i<socialMedia.size();i++){
                    if(socialMedia.get(i).name.equals("Google Plus")){

                        intent.setData(Uri.parse(socialMedia.get(i).link));
                        startActivity(intent);
                    }
                }

            }else if(v.getId()==R.id.youtube){

                for(int i=0;i<socialMedia.size();i++){
                    if(socialMedia.get(i).name.equals("YouTube")){

                        intent.setData(Uri.parse(socialMedia.get(i).link));
                        startActivity(intent);
                    }
                }
            }
        }
    }


}

