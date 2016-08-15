package com.tasker.bean;

import com.tasker.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for serializing Task objects.
 */
public class TaskStorage {

  private int nextTaskId = 1;

  private List<Task> taskList = new ArrayList<>();

  public synchronized int getNextTaskId() {
    return nextTaskId++;
  }

  public List<Task> getTaskList() {
    return taskList;
  }

  public Task putNewTask(final String title, final String description) {
    final Task task = new Task();
    task.id = getNextTaskId();
    task.title = title;
    task.description = description;
    taskList.add(task);

    return task;
  }

  public Task getTask(final int id) {
    for (Task task : taskList) {
      if (task.id == id) {
        return task;
      }
    }

    return null;
  }

  public boolean deleteTask(final int id) {
    for (Task task : taskList) {
      if (task.id == id) {
        return taskList.remove(task);
      }
    }

    return false;
  }
}
