package com.nacoda.movies.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.nacoda.movies.db.DatabaseContract;
import com.nacoda.movies.db.MovieHelper;

import static com.nacoda.movies.db.DatabaseContract.AUTHORITY;
import static com.nacoda.movies.db.DatabaseContract.CONTENT_URI;

/**
 * Created by dicoding on 12/13/2016.
 */

public class MovieProvider extends ContentProvider {

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_NOTE, NOTE);

        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_NOTE+ "/#",
                NOTE_ID);
    }

    private MovieHelper helper;

    @Override
    public boolean onCreate() {
        helper = new MovieHelper(getContext());
        helper.open();
        return true;
    }


    /*
    Method query digunakan ketika ingin menjalankan query Select
    Return cursor
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case NOTE:
                cursor = helper.queryProvider();
                break;
            case NOTE_ID:
                cursor = helper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added ;

        switch (sUriMatcher.match(uri)){
            case NOTE:
                added = helper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                updated =  helper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                deleted =  helper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

}
