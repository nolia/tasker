package com.tasker.model;

/**
 *
 */
public class Task {

  public String title;
  public String description;

  @TaskState
  public int state;
}
