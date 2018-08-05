package com.nacoda.movies.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nacoda.movies.DetailActivity;
import com.nacoda.movies.R;
import com.nacoda.movies.adapters.MovieAdapter;
import com.nacoda.movies.base.BaseFragment;
import com.nacoda.movies.loaders.NowPlayingLoader;
import com.nacoda.movies.loaders.UpcomingLoader;
import com.nacoda.movies.model.Movie;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class NowPlayingFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    @BindView(R.id.progress_bar)
    ProgressWheel progressBar;
    @BindView(R.id.recycler_movies)
    RecyclerView recyclerMovies;

    LoaderManager.LoaderCallbacks<ArrayList<Movie>> callbacks;

    @SuppressLint("ValidFragment")
    public NowPlayingFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView.LayoutManager layoutManager;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }


        recyclerMovies.setLayoutManager(layoutManager);
        recyclerMovies.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(getActivity(),
                (movie, position) -> {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(getString(R.string.detail_intent), movie);
                    startActivity(intent);
                }

        );
        recyclerMovies.setAdapter(movieAdapter);
        callbacks = this;
        getActivity().getSupportLoaderManager().initLoader(0, null, callbacks);

    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_movies;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, final Bundle loaderArgs) {
        return new NowPlayingLoader(getActivity(), progressBar);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        progressBar.setVisibility(View.INVISIBLE);
        movieAdapter.setData(data);
        recyclerMovies.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
    }

}