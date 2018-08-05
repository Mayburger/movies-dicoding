package com.nacoda.movies.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nacoda.movies.R;
import com.nacoda.movies.base.BaseFragment;

import java.util.Objects;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class PlotFragment extends BaseFragment {

    private String overview;

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @SuppressLint("ValidFragment")
    public PlotFragment() {

    }

    @BindView(R.id.overview_detail)
    TextView overviewDetail;

    @Override
    public void onStart() {
        super.onStart();
        if (overview != null) {
            overviewDetail.setText(overview);
        } else {
            overviewDetail.setText(getString(R.string.no_overview));
        }
    }

    @Override
    public int getFragmentLayout()   {
        return R.layout.fragment_plot;
    }

}
