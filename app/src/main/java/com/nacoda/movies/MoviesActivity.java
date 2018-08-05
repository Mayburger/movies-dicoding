package com.nacoda.movies;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.nacoda.movies.fragments.SearchFragment;
import com.nacoda.movies.libs.ReleaseReceiver;
import com.nacoda.movies.libs.ReminderReceiver;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    FlowingDrawer drawerLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.language_button)
    ImageView languageButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);
        setupDrawer();
        title.setText(getString(R.string.movie));
        drawerLayout.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);

        if (savedInstanceState == null) {
            SearchFragment search = new SearchFragment();
            search.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_movies, search, getString(R.string.search)).commit();
        } else {
            SearchFragment search = (SearchFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.search));
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_movies, search, getString(R.string.search)).commit();
        }

        initRelease();
        initReminder();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initRelease() {
        Intent alarmIntent = new Intent(MoviesActivity.this, ReleaseReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MoviesActivity.this, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Repeat Every 24 hours */
        int interval = 1000 * 60 * 60 * 24;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        /* Init at 8AM */
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 10);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                interval, pendingIntent);
    }

    public void initReminder() {
        Intent alarmIntent = new Intent(MoviesActivity.this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MoviesActivity.this, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Repeat Every 24 hours */
        int interval = 1000 * 60 * 60 * 24;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        /* Init at 7AM */
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                interval, pendingIntent);
    }


    public void setupDrawer() {
        navView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Intent i = new Intent(MoviesActivity.this, MovieTypeActivity.class);

        if (id == R.id.nav_nowplaying) {
            i.putExtra(getString(R.string.type), getString(R.string.now_playing));
            startActivity(i);
        } else if (id == R.id.nav_upcoming) {
            i.putExtra(getString(R.string.type), getString(R.string.upcoming));
            startActivity(i);
        } else if (id == R.id.nav_favorites) {
            i.putExtra(getString(R.string.type), getString(R.string.favorites));
            startActivity(i);
        }


        return true;
    }


    @OnClick({R.id.drawer_button, R.id.language_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drawer_button:
                drawerLayout.openMenu();
                break;
            case R.id.language_button:
                PopupMenu popup = new PopupMenu(MoviesActivity.this, languageButton);
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