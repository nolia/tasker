package com.tasker.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.tasker.model.TaskState.DONE;
import static com.tasker.model.TaskState.NEW;
import static com.tasker.model.TaskState.WORKING;

/**
 *
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({NEW, WORKING, DONE})
public @interface TaskState {
  int NEW = 0;
  int WORKING = 1;
  int DONE = 2;
}
