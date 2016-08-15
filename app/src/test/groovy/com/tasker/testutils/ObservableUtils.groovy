package com.tasker.testutils;

class ObservableUtils {

  public static <T> T now(rx.Observable<T> source) {
    return source.toBlocking().single()
  }
}