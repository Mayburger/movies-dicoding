package com.nacoda.movies;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.widget.TextView;

import com.nacoda.movies.base.BaseActivity;
import com.nacoda.movies.fragments.FavoritesFragment;
import com.nacoda.movies.fragments.NowPlayingFragment;
import com.nacoda.movies.fragments.UpcomingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieTypeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onStart() {
        super.onStart();
        String type = getIntent().getStringExtra(getString(R.string.type));

        if (type.equals(getString(R.string.upcoming))) {
            addFragmentOnTop(new UpcomingFragment(), R.id.frame_movies);
            title.setText(getString(R.string.upcoming));
        } else if (type.equals(getString(R.string.now_playing))) {
            addFragmentOnTop(new NowPlayingFragment(), R.id.frame_movies);
            title.setText(getString(R.string.now_playing));
        } else if (type.equals(getString(R.string.favorites))) {
            addFragmentOnTop(new FavoritesFragment(), R.id.frame_movies);
            title.setText(getString(R.string.favorites));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public int getActivityLayout() {
        return R.layout.activity_movie_type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.language_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.language_button:
                PopupMenu popup = new PopupMenu(MovieTypeActivity.this, findViewById(R.id.language_button));
                popup.getMenuInflater()
                        .inflate(R.menu.main, popup.getMenu());

                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.action_language) {
                        startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    }
                    return true;
                });

                popup.show(); //showing popup menu
                break;
        }
    }
}
