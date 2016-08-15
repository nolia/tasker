package com.tasker.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tasker.model.Task;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Manages {@link Task} objects.
 */
@EBean(scope = EBean.Scope.Singleton)
public class TaskManager {

  public static final String PREF_KEY = "tasks";
  private static final String PREF_NAME = "tasker_prefs";

  @RootContext
  Context context;

  SharedPreferences preferences;

  PublishSubject<List<Task>> tasksSubject = PublishSubject.create();
  Gson gson;
  TaskStorage taskStorage;

  @AfterInject
  void afterInject() {
    gson = new GsonBuilder().create();

    preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    taskStorage = loadTaskStorage();
  }

  public Observable<List<Task>> getTasks() {
    return Observable.defer(() -> Observable.just(taskStorage.getTaskList()));
  }

  public Observable<Task> addTask(final String title, final String description) {
    return Observable.defer(() -> {
      final Task stored = taskStorage.putNewTask(title, description);
      saveTaskStorage();
      return Observable.just(stored);
    });
  }

  public Observable<Task> getTaskById(final int id) {
    return Observable.defer(() -> Observable.just(taskStorage.getTask(id)));
  }

  public Observable<Task> editTask(final int id, final String title, final String description) {
    return Observable.defer(() -> {
      final Task task = taskStorage.editTask(id, title, description);
      saveTaskStorage();
      return Observable.just(task);
    });
  }

  public Observable<Boolean> deleteTask(final int id) {
    return Observable.defer(() -> {
      final boolean deleted = taskStorage.deleteTask(id);
      if (deleted) {
        notifyTaskListChanged();
      }
      return Observable.just(deleted);
    });
  }

  public Observable<List<Task>> getTasksChangedSubject() {
    return tasksSubject;
  }

  private void saveTaskStorage() {
    final String json = gson.toJson(taskStorage);
    preferences.edit().putString(PREF_KEY, json).apply();
    notifyTaskListChanged();
  }

  @NonNull
  private TaskStorage loadTaskStorage() {
    final String json = preferences.getString(PREF_KEY, null);
    if (TextUtils.isEmpty(json)) {
      return new TaskStorage();
    }

    return gson.fromJson(json, TaskStorage.class);
  }

  private void notifyTaskListChanged() {
    tasksSubject.onNext(taskStorage.getTaskList());
  }
}
