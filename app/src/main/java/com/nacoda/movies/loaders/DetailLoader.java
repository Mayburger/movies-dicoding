package com.nacoda.movies.loaders;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.nacoda.movies.R;
import com.nacoda.movies.model.Detail;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.nacoda.movies.libs.Constants.API_KEY;

/**
 * Created by Emeth on 10/31/2016.
 */

public class DetailLoader extends AsyncTaskLoader<ArrayList<Detail>> {

    private String mMovieId;

    public DetailLoader(@NonNull Context context, String mMovieId) {
        super(context);
        this.mMovieId = mMovieId;
    }

    private ArrayList<Detail> mData = null;

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    @Override
    public ArrayList<Detail> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Detail> data = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/" + mMovieId + "?api_key=" + API_KEY + "&language=" + getContext().getString(R.string.api_language);

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
                    JSONObject response = new JSONObject(result);
                    Detail movie = new Detail(response);
                    data.add(movie);
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

    public void deliverResult(ArrayList<Detail> data) {
        mData = data;
        super.deliverResult(data);
    }
}
