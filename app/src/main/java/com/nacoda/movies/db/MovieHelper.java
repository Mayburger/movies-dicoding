package com.nacoda.movies.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.nacoda.movies.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.nacoda.movies.db.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.nacoda.movies.db.DatabaseContract.TABLE_NOTE;

/**
 * Created by sidiqpermana on 11/23/16.
 */

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_NOTE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }


    /**
     * Gunakan method ini untuk ambil semua note yang ada
     * Otomatis di parsing ke dalam model Note
     *
     * @return hasil query berbentuk array model note
     */
    public ArrayList<Movie> query() {
        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        Movie note;
        if (cursor.getCount() > 0) {
            do {

                note = new Movie();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                note.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER_PATH)));
                note.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.BACKDROP_PATH)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE)));
                note.setGenre_ids(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.GENRE_IDS)));
                note.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE)));
                note.setVote_average(Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_AVERAGE))));
                note.setVote_count(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_COUNT))));
                note.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_ID))));

                arrayList.add(note);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    /**
     * Gunakan method ini untuk query insert
     *
     * @param note model note yang akan dimasukkan
     * @return id dari data yang baru saja dimasukkan
     */
    public long insert(Movie note) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseContract.MovieColumns.POSTER_PATH, note.getPoster_path());
        initialValues.put(DatabaseContract.MovieColumns.BACKDROP_PATH, note.getBackdrop_path());
        initialValues.put(DatabaseContract.MovieColumns.TITLE, note.getTitle());
        initialValues.put(DatabaseContract.MovieColumns.RELEASE_DATE, note.getRelease_date());
        initialValues.put(DatabaseContract.MovieColumns.OVERVIEW, note.getOverview());
        initialValues.put(DatabaseContract.MovieColumns.VOTE_AVERAGE, note.getVote_average());
        initialValues.put(DatabaseContract.MovieColumns.VOTE_COUNT, note.getVote_count());
        initialValues.put(DatabaseContract.MovieColumns.GENRE_IDS, note.getGenre_ids());
        initialValues.put(MOVIE_ID, note.getId());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Gunakan method ini untuk query delete
     *
     * @param id id yang akan di delete
     * @return int jumlah row yang di delete
     */
    public int delete(int id) {
        return database.delete(TABLE_NOTE, _ID + " = '" + id + "'", null);
    }


    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, MOVIE_ID + " = ?", new String[]{id});
    }

}


