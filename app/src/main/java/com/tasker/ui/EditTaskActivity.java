package com.tasker.ui;

import android.support.v7.app.ActionBar;

import com.tasker.R;

import org.androidannotations.annotations.EActivity;

/**
 *
 */
@EActivity(R.layout.activity_edit_task)
public class EditTaskActivity extends BaseActivity {

  @Override
  protected void onSetupActionBar(final ActionBar actionBar) {
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(R.string.edit_task_activity_title);
  }
}
