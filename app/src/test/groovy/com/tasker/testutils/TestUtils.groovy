package com.tasker.testutils

class TestUtils {

  public static <T> T now(rx.Observable<T> source) {
    return source.toBlocking().single()
  }
}