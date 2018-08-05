package com.nacoda.movies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.nacoda.movies.db.DatabaseContract.TABLE_NOTE;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovies";

    private static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT" +
                    ")",
            TABLE_NOTE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.POSTER_PATH,
            DatabaseContract.MovieColumns.BACKDROP_PATH,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.RELEASE_DATE,
            DatabaseContract.MovieColumns.OVERVIEW,
            DatabaseContract.MovieColumns.VOTE_AVERAGE,
            DatabaseContract.MovieColumns.VOTE_COUNT,
            DatabaseContract.MovieColumns.GENRE_IDS,
            DatabaseContract.MovieColumns.MOVIE_ID
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTE);
        onCreate(db);
    }
}
