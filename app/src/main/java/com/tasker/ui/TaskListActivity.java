package com.tasker.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;

import com.tasker.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 *
 */
@EActivity(R.layout.activity_task_list)
public class TaskListActivity extends BaseActivity {

  @ViewById(R.id.fab)
  FloatingActionButton fab;

  @Override
  protected void onSetupActionBar(final ActionBar actionBar) {
    actionBar.setTitle(R.string.task_list_title);
  }

  @Click(R.id.fab)
  void onFab() {
    EditTaskActivity_.intent(this).start();
  }

}
