package com.nacoda.favoritesapp;

import android.database.Cursor;
import com.nacoda.favoritesapp.db.DatabaseContract;
import org.json.JSONObject;
import java.io.Serializable;

import static com.nacoda.favoritesapp.db.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.nacoda.favoritesapp.db.DatabaseContract.getColumnString;

/**
 * Created by Mayburger on 1/11/18.
 */


public class Movie implements Serializable {

    private int vote_count;
    private float vote_average;

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(String genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    private int id;
    private String video;
    private String title;
    private int popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String genre_ids;
    private String backdrop_path;
    private String adult;
    private String overview;
    private String release_date;

    public Movie() {
    }

    public Movie(Cursor cursor) {
        this.id = Integer.parseInt(getColumnString(cursor, MOVIE_ID));
        this.poster_path = getColumnString(cursor, DatabaseContract.MovieColumns.POSTER_PATH);
        this.backdrop_path = getColumnString(cursor, DatabaseContract.MovieColumns.BACKDROP_PATH);
        this.title = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.release_date = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);
        this.overview = getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.genre_ids = getColumnString(cursor, DatabaseContract.MovieColumns.GENRE_IDS);
        this.vote_average = Float.parseFloat(getColumnString(cursor, DatabaseContract.MovieColumns.VOTE_AVERAGE));
        this.vote_count = Integer.parseInt(getColumnString(cursor, DatabaseContract.MovieColumns.VOTE_COUNT));
    }

    public Movie(JSONObject object) {

        try {
            int vote_count = object.getInt("vote_count");
            float vote_average = object.getInt("vote_average");
            int id = object.getInt("id");
            String video = object.getString("video");
            String title = object.getString("title");
            int popularity = object.getInt("popularity");
            String poster_path = object.getString("poster_path");
            String original_language = object.getString("original_language");
            String original_title = object.getString("original_title");
            String genre_ids = object.getString("genre_ids");
            String backdrop_path = object.getString("backdrop_path");
            String adult = object.getString("adult");
            String overview = object.getString("overview");
            String release_date = object.getString("release_date");

            this.vote_count = vote_count;
            this.vote_average = vote_average;
            this.id = id;
            this.video = video;
            this.title = title;
            this.popularity = popularity;
            this.poster_path = poster_path;
            this.original_language = original_language;
            this.original_title = original_title;
            this.genre_ids = genre_ids;
            this.backdrop_path = backdrop_path;
            this.adult = adult;
            this.overview = overview;
            this.release_date = release_date;
        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
