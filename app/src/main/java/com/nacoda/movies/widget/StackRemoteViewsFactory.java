package com.nacoda.movies.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.nacoda.movies.R;
import com.nacoda.movies.model.Movie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.nacoda.movies.db.DatabaseContract.CONTENT_URI;

/**
 * Created by dicoding on 1/9/2017.
 */


class StackRemoteViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Movie> mMovieItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        Cursor data = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);

        assert data != null;
        for (int i = 0; i < data.getCount(); i++) {
            data.moveToPosition(i);
            Movie movie = new Movie(data);
            mMovieItems.add(movie);
        }

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mMovieItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;
        try {

            String IMAGE_PATH = "http://image.tmdb.org/t/p/w780/";
            bmp = Glide.with(mContext)
                    .asBitmap()

                    .load(IMAGE_PATH + mMovieItems.get(position).getBackdrop_path())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

        rv.setImageViewBitmap(R.id.imageView, bmp);


        Bundle extras = new Bundle();
        extras.putInt(ImagesBannerWidget.EXTRA_ITEM, position);
        extras.putString(ImagesBannerWidget.MOVIE_NAME, mMovieItems.get(position).getTitle());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}