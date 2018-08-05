package com.nacoda.movies.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class DatabaseContract {
    public static String TABLE_NOTE = "movie";

    public static final class MovieColumns implements BaseColumns {
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String TITLE = "title";
        public static final String RELEASE_DATE = "release_date";
        public static final String OVERVIEW = "overview";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";
        public static final String GENRE_IDS = "genre_ids";
        public static final String MOVIE_ID = "movie_id";
    }

    public static final String AUTHORITY = "com.nacoda.movies";


    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NOTE)
            .build();


    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

}
