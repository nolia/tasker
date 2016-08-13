package com.tasker.ui;

import android.support.v7.app.ActionBar;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tasker.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 *
 */
@EActivity(R.layout.activity_edit_task)
public class EditTaskActivity extends BaseActivity {

  @ViewById(R.id.taskStateLayout)
  LinearLayout stateLayout;

  @Override
  protected void afterViews() {
    super.afterViews();
    for (int i = 0; i < stateLayout.getChildCount(); i++) {
      final int finalI = i;
      stateLayout.getChildAt(i).setOnClickListener(view -> Toast.makeText(EditTaskActivity.this, "AAAA, view " + finalI, Toast.LENGTH_SHORT).show());
    }
  }

  @Override
  protected void onSetupActionBar(final ActionBar actionBar) {
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(R.string.edit_task_activity_title);
  }
}
