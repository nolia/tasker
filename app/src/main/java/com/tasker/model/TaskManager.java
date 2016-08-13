package com.tasker.model;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Manages {@link Task} objects.
 */
@EBean(scope = EBean.Scope.Singleton)
public class TaskManager {

  List<Task> tasks = new ArrayList<>();

  PublishSubject<List<Task>> tasksSubject = PublishSubject.create();

  public Observable<List<Task>> getTasks() {
    return Observable.defer(() -> Observable.just(tasks));
  }

  public Observable<Boolean> addTask(final String title, final String description) {
    return Observable.defer(() -> {
      final Task task = new Task();
      task.title = title;
      task.description = description;
      tasks.add(task);

      tasksSubject.onNext(tasks);
      return Observable.just(true);
    });
  }



  public Observable<List<Task>> getTasksChangedSubject() {
    return tasksSubject;
  }
}
