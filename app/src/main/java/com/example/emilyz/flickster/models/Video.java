package com.example.emilyz.flickster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by emilyz on 6/23/17.
 */

public class Video {
    private String videoKey;
    private String videoType;


    public Video(){
        super();
    }

    public Video(JSONObject jsonObject) throws JSONException {
        videoKey  = jsonObject.getString("key");
        videoType = jsonObject.getString("type");
    }

    public String getVideoKey() {
        return videoKey;
    }

    public String getVideoType() {
        return videoType;
    }
    public static ArrayList<Video> fromJSONArray(JSONArray array){
        ArrayList<Video> results = new ArrayList<>();

        for(int i=0; i<array.length(); i++){
            try {
                results.add(new Video(array.getJSONObject(i)));
            } catch (JSONException e) {
                Log.e("MovieTrailerActivity", "Error initializing YouTube trailer list");
            }
        }
        return results;
    }
}
