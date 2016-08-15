package com.tasker.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.View;

import com.tasker.R;
import com.tasker.model.TaskState;

/**
 *
 */
public class Utils {

  public static final SparseIntArray TASK_STATE_COLORS = new SparseIntArray();

  static {
    Utils.TASK_STATE_COLORS.put(TaskState.NEW, R.color.taskStateNew);
    Utils.TASK_STATE_COLORS.put(TaskState.WORKING, R.color.taskStateWorking);
    Utils.TASK_STATE_COLORS.put(TaskState.DONE, R.color.taskStateDone);
  }

  @ColorInt
  static int getTaskStateColor(final Context context, final int state) {
    return ContextCompat.getColor(context, TASK_STATE_COLORS.get(state, R.color.taskStateNew));
  }

  public static void setSelectableBackground(final View view) {
    final Context context = view.getContext();
    int[] attrs = new int[]{android.R.attr.selectableItemBackground};

    final TypedArray ta = context.obtainStyledAttributes(attrs);
    final Drawable selectableItemBackground = ta.getDrawable(0 /* index */);
    ta.recycle();

    view.setBackground(selectableItemBackground);
  }
}
