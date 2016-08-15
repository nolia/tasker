package com.tasker.bean

import com.tasker.BuildConfig
import com.tasker.testutils.ObservableUtils
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification
import rx.functions.Action1
import spock.util.mop.Use

/**
 *
 */
@Use(ObservableUtils)
@Config(constants = BuildConfig, sdk = 21)
class TaskManagerSpec extends RoboSpecification {

  TaskManager taskManager

  void setup() {
    taskManager = TaskManager_.getInstance_(RuntimeEnvironment.application)

  }

  def "list is updated when user adds new task"() {
    given: 'We subscribed to list'
    def listObserver = Mock(Action1)
    taskManager.tasksChangedSubject.subscribe(listObserver)

    when: 'User adds new task'
    taskManager.addTask('New task', 'Some description').now()

    then: 'List subscriber is called'
    1 * listObserver.call(_)


  }
}
