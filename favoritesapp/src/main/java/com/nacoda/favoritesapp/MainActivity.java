package com.nacoda.favoritesapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nacoda.favoritesapp.adapters.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nacoda.favoritesapp.db.DatabaseContract.CONTENT_URI;
import static com.nacoda.favoritesapp.libs.Constants.IMAGE_URL;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.title)
//    TextView title;
//    @BindView(R.id.language_button)
//    ImageView languageButton;
    @BindView(R.id.recycler_movies)
    RecyclerView recyclerMovies;

    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        movieAdapter = new MovieAdapter(getApplicationContext());

        Cursor data = getContentResolver().query(CONTENT_URI, null, null, null, null);

        List<Movie> list = new ArrayList<>();
        assert data != null;
        for (int i = 0; i < data.getCount(); i++){
            data.moveToPosition(i);
            Movie movie = new Movie(data);
            list.add(movie);
        }

        movieAdapter.setData(list);
        recyclerMovies.setAdapter(movieAdapter);
        recyclerMovies.setLayoutManager(new LinearLayoutManager(this));
    }
    @OnClick({R.id.language_button})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.language_button:
                PopupMenu popup = new PopupMenu(MainActivity.this, findViewById(R.id.language_button));
                popup.getMenuInflater()
                        .inflate(R.menu.main, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_language) {
                            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
                break;
        }
    }
}


