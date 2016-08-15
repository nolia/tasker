package com.tasker.ui;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tasker.R;
import com.tasker.model.Task;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@EActivity(R.layout.activity_task_list)
public class TaskListActivity extends BaseActivity {

  @ViewById(R.id.fab)
  FloatingActionButton fab;

  @ViewById(R.id.recyclerView)
  protected RecyclerView recyclerView;

  @ViewById(R.id.progressBar)
  protected View progressBar;

  protected TaskRecyclerAdapter adapter;

  @Override
  protected void afterViews() {
    super.afterViews();
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new TaskRecyclerAdapter(this);
    recyclerView.setAdapter(adapter);

    loadData();

    taskManager.getTasksChangedSubject().subscribe(
        adapter::setItems
    );
  }

  void loadData() {
    taskManager.getTasks()
        .doOnSubscribe(() -> showProgress(true))
        .doOnEach(ignored -> showProgress(false))
        .subscribe(adapter::setItems);
  }

  void showProgress(boolean show) {
    if (show) {
      recyclerView.setVisibility(View.GONE);
      progressBar.setVisibility(View.VISIBLE);
    } else {
      recyclerView.setVisibility(View.VISIBLE);
      progressBar.setVisibility(View.GONE);
    }
  }

  @Override
  protected void onSetupActionBar(final ActionBar actionBar) {
    actionBar.setTitle(R.string.task_list_title);
  }

  @Click(R.id.fab)
  void onFab() {
    EditTaskActivity_.intent(this)
        .action(EditTaskActivity.ACTION_NEW)
        .start();
  }

  private class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private final List<Task> items = new ArrayList<>();
    LayoutInflater layoutInflater;

    public TaskRecyclerAdapter(final Context context) {
      layoutInflater = LayoutInflater.from(context);
    }

    public void setItems(final List<Task> items) {
      this.items.clear();
      this.items.addAll(items);
      notifyDataSetChanged();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
      return new TaskViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
      // Bind data here.
      final Task task = items.get(position);

      holder.title.setText(task.title);
      holder.description.setText(task.description);

      holder.rootView.setBackgroundColor(Utils.getTaskStateColor(TaskListActivity.this, task.state));
      holder.rootView.setOnClickListener(view ->
          EditTaskActivity_.intent(TaskListActivity.this)
              .action(EditTaskActivity.ACTION_EDIT)
              .taskId(task.id)
              .start()
      );
    }

    @Override
    public int getItemCount() {
      return items.size();
    }
  }

  private class TaskViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView description;
    View rootView;

    TextView actionButton;

    public TaskViewHolder(final View itemView) {
      super(itemView);
      rootView = itemView;
      title = ((TextView) itemView.findViewById(R.id.taskTitle));
      description = (TextView) itemView.findViewById(R.id.taskDescription);

      actionButton = (TextView) itemView.findViewById(R.id.actionButton);

    }
  }
}
