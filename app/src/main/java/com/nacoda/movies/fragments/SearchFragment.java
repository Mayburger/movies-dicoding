package com.nacoda.movies.fragments;


import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.nacoda.movies.DetailActivity;
import com.nacoda.movies.R;
import com.nacoda.movies.adapters.MovieAdapter;
import com.nacoda.movies.base.BaseFragment;
import com.nacoda.movies.loaders.SearchLoader;
import com.nacoda.movies.model.Movie;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

import butterknife.BindView;

import static com.nacoda.movies.libs.Constants.QUERY;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SearchFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    @BindView(R.id.progress_bar)
    ProgressWheel progressBar;
    @BindView(R.id.recycler_movies)
    RecyclerView recyclerMovies;
    @BindView(R.id.search)
    EditText search;


    @SuppressLint("ValidFragment")
    public SearchFragment() {
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

        movieAdapter = new MovieAdapter(getActivity(),
                (movie, position) -> {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(getString(R.string.detail_intent), movie);
                    startActivity(intent);
                }

        );

        movieAdapter.notifyDataSetChanged();

        recyclerMovies.setAdapter(movieAdapter);
        recyclerMovies.setLayoutManager(layoutManager);


        if (savedInstanceState != null) {
            search.setText(savedInstanceState.getString(getString(R.string.query)));
        }

        search.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                if (!TextUtils.isEmpty(search.getText().toString())) {
                    progressBar.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString(QUERY, search.getText().toString());
                    getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
                    hideInputMethod();
                }

            }
            return true;
        });


        Bundle bundle = new Bundle();
        bundle.putString(QUERY, search.getText().toString());
        getLoaderManager().initLoader(0, bundle, SearchFragment.this);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.query), search.getText().toString());
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        String mQuery = "";
        if (args != null) {
            mQuery = args.getString(QUERY);
        }

        return new SearchLoader(getActivity(), mQuery);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        movieAdapter.setData(data);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Movie>> loader) {
        movieAdapter.setData(null);
    }

    /**
     * Method to hide the SoftKeyboard
     **/
    public void hideInputMethod() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_search;
    }
}