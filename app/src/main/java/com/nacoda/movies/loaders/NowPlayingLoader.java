package com.nacoda.movies.loaders;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.view.View;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.nacoda.movies.R;
import com.nacoda.movies.model.Movie;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.nacoda.movies.libs.Constants.API_KEY;

/**
 * Created by Mayburger on 1/11/18.
 */

public class NowPlayingLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    public NowPlayingLoader(@NonNull Context context, ProgressWheel progressBar) {
        super(context);
        this.progressBar = progressBar;
    }

    @SuppressLint("StaticFieldLeak")
    private ProgressWheel progressBar;
    private ArrayList<Movie> mData = null;

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            forceLoad();
        }
    }

    @Override
    public ArrayList<Movie> loadInBackground() {

        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movie> data = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=" + getContext().getString(R.string.api_language);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movies = list.getJSONObject(i);
                        Movie movie = new Movie(movies);
                        data.add(movie);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return data;
    }

    public void deliverResult(ArrayList<Movie> data) {
        mData = data;
        super.deliverResult(data);
    }
}
