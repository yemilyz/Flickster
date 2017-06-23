package com.example.emilyz.flickster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.example.emilyz.flickster.MovieListActivity.API_BASE_URL;
import static com.example.emilyz.flickster.MovieListActivity.API_KEY_PARAM;
import com.example.emilyz.flickster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    Movie movie;

    AsyncHttpClient client;

    public final static String TAG = "MovieTrailerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        //initialize client
        client = new AsyncHttpClient();

        // temporary test video id -- TODO replace with movie trailer video id
        final String videoId = "tKodtNFpzBA";

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.api_youtube), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                //play video
                youTubePlayer.loadVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
                Toast.makeText(MovieTrailerActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getTrailer() {
        //create url
        String url = API_BASE_URL + "/movie/" + movie.getId() + "/videos";
        //set request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        //execute a GET request expecting a JSON object response
        client.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    if (results.length() > 0){
                        Movie key = new Movie(results.getJSONObject(0));
                    }
                } catch (JSONException e) {
                    logError("Failed to parse movie id", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get error from {movie_id}/videos endpoint", throwable,true);
            }
        });
    }
    private void logError(String message, Throwable error, boolean alertUser){
        //always log error
        Log.e(TAG, message, error);
        //alert user, no silent error
        if (alertUser){
            //long toast with error message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }


}