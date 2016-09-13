package com.tasker.ui;

import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tasker.R;
import com.tasker.model.Task;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import rx.Observable;

/**
 *
 */
@OptionsMenu(R.menu.activity_edit_task)
@EActivity(R.layout.activity_edit_task)
public class EditTaskActivity extends BaseActivity {

  public static final String ACTION_NEW = "new";
  public static final String ACTION_EDIT = "edit";

  @Extra
  int taskId;

  @OptionsMenuItem(R.id.delete_item)
  MenuItem deleteMenuItem;

  @ViewById(R.id.taskStateLayout)
  protected LinearLayout stateLayout;

  @ViewById(R.id.taskTitle)
  protected TextView taskTitle;

  @ViewById(R.id.taskDescription)
  protected TextView taskDescription;

  int taskState;

  @Override
  protected void afterViews() {
    super.afterViews();
    for (int i = 0; i < stateLayout.getChildCount(); i++) {
      final int index = i;
      stateLayout.getChildAt(i).setOnClickListener(view -> setTaskStateIndex(index));
    }

    if (isEditing()) {
      taskManager.getTaskById(taskId).subscribe(this::bindData);
    }

    stateLayout.setVisibility(isEditing() ? View.VISIBLE : View.GONE);
  }

  @Override
  public boolean onPrepareOptionsMenu(final Menu menu) {
    deleteMenuItem.setVisible(isEditing());

    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  protected void onSetupActionBar(final ActionBar actionBar) {
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle(isEditing() ? R.string.title_edit_task : R.string.title_add_task);
  }

  @OptionsItem(R.id.delete_item)
  void onDeleteClick() {
    taskManager.deleteTask(taskId).subscribe();
    finish();
  }

  @Click(R.id.fab)
  void onFabClick() {
    if (isEditing()) {
      editTask();
    } else {
      createNewTask();
    }
    finish();
  }

  private void setTaskStateIndex(final int index) {
    taskState = index;
    updateStateViews();
  }

  private void updateStateViews() {
    for (int i = 0; i < stateLayout.getChildCount(); i++) {
      final View view = stateLayout.getChildAt(i);
      if (i == taskState) {
        view.setBackgroundColor(Utils.getTaskStateColor(this, taskState));
      } else {
        Utils.setSelectableBackground(view);
      }
    }
  }

  private void bindData(final Task task) {
    taskTitle.setText(task.title);
    taskDescription.setText(task.description);
    setTaskStateIndex(task.state);

  }

  private void editTask() {
    final String title = taskTitle.getText().toString();
    final String description = taskDescription.getText().toString();

    Observable.zip(
        taskManager.editTask(taskId, title, description),
        taskManager.setTaskState(taskId, taskState),
        (t1, t2) -> t1
    ).subscribe();
  }

  private void createNewTask() {
    final String title = taskTitle.getText().toString();
    final String description = taskDescription.getText().toString();

    taskManager.addTask(title, description).subscribe();
  }

  private boolean isEditing() {
    return ACTION_EDIT.equals(getIntent().getAction());
  }


}
