package com.ahdollars.crazyeights.internetlecture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.ahdollars.crazyeights.internetlecture.model.Event;
import com.ahdollars.crazyeights.internetlecture.model.Social;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Shashank on 01-10-2016.
 */

public class NetFetchTask extends AsyncTask<String,Void,ArrayList<Event>> {

public static final String TAG="BATMAN";
    public ArrayList<Event> event=new ArrayList<>();

    @Override
    protected ArrayList<Event> doInBackground(String... params) {
        URL imageURL=null;
        try {
            URL url=new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //since page will come to our app so it is a input
            urlConnection.setDoInput(true);  //by default it is true so not needed
            urlConnection.connect();
            //in return we get status code,headers,body
            //we only need only the body part
            if(urlConnection.getResponseCode()==200){
            /*    //bingo! conection successful
                InputStream is=urlConnection.getInputStream();//cuz we have tom read a large page
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                StringBuilder sb=new StringBuilder();
                String buffer="";
                while((buffer=br.readLine())!=null){
                    sb.append(buffer);
                }
                return sb.toString();
                */

                InputStream is=urlConnection.getInputStream();//cuz we have tom read a large page
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                StringBuilder sb=new StringBuilder();
                String buffer="";
                while((buffer=br.readLine())!=null){
                    sb.append(buffer);
                }


                 String organiserName;
                 String discription;
                 String topic;
                 String identifier;
                 String schedule;
                 String background;
                 String location;
                int id;
                boolean sessionenable;
                JSONArray jArray= new JSONArray(sb.toString());

                for(int i=0;i<jArray.length();i++){
                    JSONObject obj= (JSONObject) jArray.get(i);
                    organiserName= obj.getString("name");
                    discription=(String)obj.get("description");
                    background=obj.getString("background_image");
                    topic=obj.getString("topic");
                    schedule=obj.getString("start_time");
                    identifier=obj.getString("identifier");
                    location=obj.getString("location_name");
                    id=obj.getInt("id");
                    sessionenable=obj.getBoolean("has_session_speakers");
                    JSONArray array= obj.getJSONArray("social_links");
                    ArrayList<Social> social=new ArrayList<>();
                    Log.d(TAG, "SOCIAL LINKS "+array.length());
                    for(int j=0;j<array.length();j++){
                        JSONObject ob= array.getJSONObject(j);
                        social.add(new Social(ob.getInt("id"),ob.getString("link"),ob.getString("name")));
                    }
                    event.add(new Event(background,discription,identifier,location,organiserName,schedule,social,topic,identifier+".jpg",id,sessionenable));
                   try{
                       File photoFile=new File(Environment.getExternalStorageDirectory(),identifier+".jpg");
                       if(photoFile.exists()){
                           continue;
                       }
                       if(background.length()==0){
                           continue;
                       }
                       imageURL=new URL(background);
                       HttpURLConnection imgConnection= (HttpURLConnection)imageURL.openConnection();
                       imgConnection.setDoInput(true);
                       imgConnection.connect();
                       Bitmap photo= BitmapFactory.decodeStream(imgConnection.getInputStream());

                       FileOutputStream f=new FileOutputStream(photoFile,false);
                       photo.compress(Bitmap.CompressFormat.JPEG,100,f);
                       f.flush();
                       f.close();
                   }catch(MalformedURLException e){

                   }


                }
                Log.d(TAG,"SIZE OF RETURNED"+event.size());

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, "doInBackground: "+e.toString()+"   "+imageURL.toString());

            return event;

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "doInBackground: "+e.toString());
            return event;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "doInBackground: "+e.toString());
            return event;

        }
        Log.d(TAG, "RETURNING NULL ARRAYLIST: ");
        return event;

    }


}
