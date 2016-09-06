package com.tasker.model;

/**
 *
 */
public class Task {

  public int id;

  public String title;
  public String description;

  @TaskState
  public int state;

  public Task() {
    // Default.
  }

  public Task(final int id, final String title) {
    this.id = id;
    this.title = title;
  }
}
