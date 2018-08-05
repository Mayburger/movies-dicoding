package com.nacoda.movies.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;


import com.facebook.stetho.Stetho;

import butterknife.ButterKnife;

/**
 * Created by Mayburger on 10/01/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Activity activity;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        activity = this;
    }

    public void addFragmentOnTop(Fragment fragment, int layout) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(layout, fragment)
                .addToBackStack("")
                .commit();
    }

    public abstract
    @LayoutRes
    int getActivityLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        Stetho.initializeWithDefaults(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        activity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
        activity = this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
