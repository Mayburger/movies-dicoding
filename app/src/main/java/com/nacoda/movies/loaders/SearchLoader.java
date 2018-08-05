package com.nacoda.movies.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.nacoda.movies.R;
import com.nacoda.movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.nacoda.movies.libs.Constants.API_KEY;

/**
 * Created by Emeth on 10/31/2016.
 */

public class SearchLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private ArrayList<Movie> mData;
    private boolean mHasResult = false;

    private String mQuery;

    public SearchLoader(final Context context, String mQuery) {
        super(context);
        onContentChanged();
        this.mQuery = mQuery;
    }


    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<Movie> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movie> data = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/search/movie" +
                "?api_key=" + API_KEY +
                "&language=" + getContext().getString(R.string.api_language) +
                "&query=" + mQuery;


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

    protected void onReleaseResources(ArrayList<Movie> data) {

    }
}
