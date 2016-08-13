package com.tasker.ui;

import android.support.annotation.CallSuper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tasker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

/**
 *
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {

  @ViewById(R.id.toolbar)
  Toolbar toolbar;

  @CallSuper
  @AfterViews
  protected void afterViews() {
    setSupportActionBar(toolbar);
    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      onSetupActionBar(actionBar);
    }
  }

  protected void onSetupActionBar(final ActionBar actionBar) {
    // Left for rent.
  }

  @OptionsItem(android.R.id.home)
  protected void onHome() {
    onBackPressed();
  }
}
