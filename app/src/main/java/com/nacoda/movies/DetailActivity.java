package com.nacoda.movies;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.eightbitlab.supportrenderscriptblur.SupportRenderScriptBlur;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.nacoda.movies.fragments.PlotFragment;
import com.nacoda.movies.libs.Favorites;
import com.nacoda.movies.loaders.DetailLoader;
import com.nacoda.movies.model.Detail;
import com.nacoda.movies.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eightbitlab.com.blurview.BlurView;

import static com.nacoda.movies.libs.Constants.IMAGE_URL;
import static com.nacoda.movies.libs.Constants.MOVIE_ID;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Detail>> {

    Movie movie;
    @BindView(R.id.backdrop_detail)
    ImageView backdropDetail;
    @BindView(R.id.title_detail)
    TextView titleDetail;
    @BindView(R.id.vote_average_detail)
    TextView voteAverageDetail;
    @BindView(R.id.vote_count_detail)
    TextView voteCountDetail;
    @BindView(R.id.duration_detail)
    TextView durationDetail;
    @BindView(R.id.yearDetail)
    TextView yearDetail;
    @BindView(R.id.donut_progress_detail)
    DonutProgress donutProgressDetail;
    @BindView(R.id.progress_text)
    TextView progressText;
    @BindView(R.id.poster_detail)
    ImageView posterDetail;

    @BindView(R.id.poster_detail_header)
    ImageView posterDetailHeader;

    @BindView(R.id.blurView)
    BlurView blurView;
    @BindView(R.id.root)
    RelativeLayout root;

    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;

    LoaderManager.LoaderCallbacks<ArrayList<Detail>> callbacks;

    String detailFragmentType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        movie = (Movie) getIntent().getSerializableExtra(getString(R.string.detail_intent));
        new Favorites().with(this).initMovie(movie, findViewById(R.id.favorite), findViewById(R.id.unfavorite));

        callbacks = this;

        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_ID, String.valueOf(movie.getId()));
        getSupportLoaderManager().initLoader(0, bundle, callbacks);

        PlotFragment plot = new PlotFragment();
        plot.setOverview(movie.getOverview());
        addFragmentOnTop(plot, R.id.frame_detail, getString(R.string.plot));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.type), detailFragmentType);
    }

    @Override
    public Loader<ArrayList<Detail>> onCreateLoader(int id, Bundle args) {
        String mQuery = "";
        if (args != null) {
            mQuery = args.getString(MOVIE_ID);
        }

        return new DetailLoader(this, mQuery);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initDetail(Detail detail) {
        progressLayout.setVisibility(View.GONE);
        Glide.with(getApplicationContext()).load(IMAGE_URL + movie.getBackdrop_path()).apply(new RequestOptions().centerCrop()).transition(DrawableTransitionOptions.withCrossFade()).into(backdropDetail);
        Glide.with(getApplicationContext()).load(IMAGE_URL + movie.getPoster_path()).apply(new RequestOptions().centerCrop()).transition(DrawableTransitionOptions.withCrossFade()).into(posterDetail);
        Glide.with(getApplicationContext()).load(IMAGE_URL + movie.getPoster_path()).apply(new RequestOptions().centerCrop()).transition(DrawableTransitionOptions.withCrossFade()).into(posterDetailHeader);
        titleDetail.setText(movie.getTitle());
        voteAverageDetail.setText(String.valueOf(Math.round(movie.getVote_average())) + getString(R.string.average));
        voteCountDetail.setText(String.valueOf(movie.getVote_count()) + " " + getString(R.string.user_votes));
        durationDetail.setText(detail.getRuntime() + " " + getString(R.string.minutes));
        yearDetail.setText(movie.getRelease_date().substring(0, 4));
        donutProgressDetail.setText("");
        donutProgressDetail.setDonut_progress(String.valueOf(Math.round((movie.getVote_average() * 10))));
        progressText.setText(Math.round((movie.getVote_average() * 10)) + "%");
        final Drawable windowBackground = getWindow().getDecorView().getBackground();
        blurView.setupWith(root)
                .windowBackground(windowBackground)
                .blurAlgorithm(new SupportRenderScriptBlur(this))
                .blurRadius(15f);
    }

    public void addFragmentOnTop(Fragment fragment, int layout, String type) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(layout, fragment)
                .addToBackStack("")
                .commit();

        detailFragmentType = type;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Detail>> loader, ArrayList<Detail> data) {
        initDetail(data.get(0));
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Detail>> loader) {

    }
}