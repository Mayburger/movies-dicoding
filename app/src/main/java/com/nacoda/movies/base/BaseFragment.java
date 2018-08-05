package com.nacoda.movies.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nacoda.movies.adapters.MovieAdapter;

import butterknife.ButterKnife;

/**
 * Created by Mayburger on 1/29/18.
 */

public abstract class BaseFragment extends Fragment {

    public MovieAdapter movieAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    public abstract
    @LayoutRes
    int getFragmentLayout();


}
