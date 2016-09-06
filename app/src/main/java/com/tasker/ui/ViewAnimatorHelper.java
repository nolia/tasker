package com.tasker.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 *
 */
public class ViewAnimatorHelper {

  public void animateError(final View view) {
    final ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 20);
    animator.setDuration(40);
    animator.setRepeatMode(ValueAnimator.REVERSE);
    animator.setRepeatCount(5);

    animator.start();
  }
}
