package com.example.emilyz.flickster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by emilyz on 6/21/17.
 */

@Parcel //annotation indicates class is Parcelable
public class Movie {
    //values from API
    // fields must be public for parceler
    String title;
    String overview;
    String posterPath; //only the path
    String backDropPath;
    Double voteAverage;
    int id;

    // no-arg, empty constructor required for Parceler
    public Movie() { }


    //initialize from JSON data
    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backDropPath = object.getString("backdrop_path");
        voteAverage = object.getDouble("vote_average");
        id = object.getInt("id");

    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackDropPath() {

        return backDropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }
}
