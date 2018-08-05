package com.nacoda.movies.libs;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.nacoda.movies.R;
import com.nacoda.movies.db.DatabaseContract;
import com.nacoda.movies.db.MovieHelper;
import com.nacoda.movies.model.Movie;

import org.apache.commons.cli.PosixParser;

import static com.nacoda.movies.db.DatabaseContract.CONTENT_URI;
import static com.nacoda.movies.db.DatabaseContract.MovieColumns.BACKDROP_PATH;
import static com.nacoda.movies.db.DatabaseContract.MovieColumns.GENRE_IDS;
import static com.nacoda.movies.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.nacoda.movies.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.nacoda.movies.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.nacoda.movies.db.DatabaseContract.MovieColumns.TITLE;
import static com.nacoda.movies.db.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.nacoda.movies.db.DatabaseContract.MovieColumns.VOTE_COUNT;
import static com.nacoda.movies.db.DatabaseContract.getColumnInt;
import static com.nacoda.movies.db.DatabaseContract.getColumnString;
import static com.nacoda.movies.libs.Constants.MOVIE_ID;

/**
 * Created by Mayburger on 2/5/18.
 */

public class Favorites {

    private Context context;

    public Favorites with(Context context) {
        this.context = context;
        return this;
    }


    public void initMovie(Movie data, View favorite, View unfavorite) {

        MovieHelper movieHelper = new MovieHelper(context);
        movieHelper.open();

        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, DatabaseContract.MovieColumns.MOVIE_ID, new String[]{String.valueOf(data.getId())}, null);
        cursor.moveToNext();


        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String id = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_ID);
            if (id.equals(String.valueOf(data.getId()))) {
                unfavorite.setVisibility(View.VISIBLE);
                favorite.setVisibility(View.GONE);
                break;
            } else {
                unfavorite.setVisibility(View.GONE);
                favorite.setVisibility(View.VISIBLE);
                break;
            }

        }

        favorite.setOnClickListener(view -> {
            unfavorite.setVisibility(View.VISIBLE);
            unfavorite.startAnimation(AnimationUtils.loadAnimation(context, R.anim.pop));
            favorite.setVisibility(View.GONE);

            ContentValues values = new ContentValues();
            values.put(POSTER_PATH, data.getPoster_path());
            values.put(BACKDROP_PATH, data.getBackdrop_path());
            values.put(TITLE, data.getTitle());
            values.put(RELEASE_DATE, data.getRelease_date());
            values.put(OVERVIEW, data.getOverview());
            values.put(GENRE_IDS, data.getGenre_ids());
            values.put(VOTE_AVERAGE, data.getVote_average());
            values.put(VOTE_COUNT, data.getVote_count());
            values.put(MOVIE_ID, data.getId());
            context.getContentResolver().insert(CONTENT_URI, values);
        });

        unfavorite.setOnClickListener(view -> {

            Uri uris = Uri.parse(CONTENT_URI+"/"+data.getId());

            unfavorite.setVisibility(View.GONE);
            favorite.setVisibility(View.VISIBLE);
            favorite.startAnimation(AnimationUtils.loadAnimation(context, R.anim.pop));
            context.getContentResolver().delete(uris,null,null);
        });

    }
}
