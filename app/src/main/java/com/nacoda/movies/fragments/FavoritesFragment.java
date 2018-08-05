package com.nacoda.movies.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.nacoda.movies.DetailActivity;
import com.nacoda.movies.R;
import com.nacoda.movies.adapters.MovieAdapter;
import com.nacoda.movies.base.BaseFragment;
import com.nacoda.movies.model.Movie;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.nacoda.movies.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FavoritesFragment extends BaseFragment {

    @BindView(R.id.progress_bar)
    ProgressWheel progressBar;
    @BindView(R.id.recycler_movies)
    RecyclerView recyclerMovies;

    @SuppressLint("ValidFragment")
    public FavoritesFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();

        progressBar.setVisibility(View.GONE);

        movieAdapter = new MovieAdapter(getActivity(),
                (movie, position) -> {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(getString(R.string.detail_intent), movie);
                    startActivity(intent);
                }

        );

        Cursor data = getActivity().getContentResolver().query(CONTENT_URI, null, null, null, null);

        List<Movie> list = new ArrayList<>();
        assert data != null;
        for (int i = 0; i < data.getCount(); i++){
            data.moveToPosition(i);
            Movie movie = new Movie(data);
            list.add(movie);
        }

        movieAdapter.setData(list);
        recyclerMovies.setAdapter(movieAdapter);
        RecyclerView.LayoutManager layoutManager;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }

        recyclerMovies.setLayoutManager(layoutManager);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_movies;
    }
}