package com.example.emilyz.flickster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.emilyz.flickster.models.Config;
import com.example.emilyz.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.example.emilyz.flickster.MovieListActivity.API_BASE_URL;
import static com.example.emilyz.flickster.MovieListActivity.API_KEY_PARAM;


public class MovieDetailsActivity extends AppCompatActivity {

    //movie to display
    Movie movie;
    Config config;

    //view objects
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.rbVoteAverage) RatingBar rbVoteAverage;
    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;
    @Nullable @BindView(R.id.ibVideo) ImageButton ibVideo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize client


        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        //resolve view objects
        /**tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);*/

        //unwrap the movie passed via intent, using its simple name as key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        config = (Config) Parcels.unwrap(getIntent().getParcelableExtra(Config.class.getSimpleName()));
        Log.d("MovieDetailsActivity m", String.format("Showing details for '%s'", movie.getTitle()));
        Log.d("MovieDetailsActivity c", String.format("Showing details for '%s'", config.getImageBaseUrl()));

        //set title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        //voter average is 0-10, convert to 0.5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();

        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        //set release date
        tvReleaseDate.setText("Release date: "+ movie.getReleaseDate());

        String imgfile = String.format("%s%s%s", config.getImageBaseUrl(), config.getOtherSize(), movie.getBackDropPath()); //concatenate all three
        Glide.with(this)
                .load(imgfile)
                .override(140, 450)
                .bitmapTransform(new RoundedCornersTransformation(this, 25, 0))
                .into(ibVideo);

        ibVideo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                if(intent != null){
                    intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                    startActivity(intent);
                }
            }
        });


    }
}
