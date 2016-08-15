package com.tasker.ui

import com.tasker.BuildConfig
import com.tasker.bean.TaskManager
import com.tasker.model.Task
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification
import rx.Observable

import static org.robolectric.Shadows.shadowOf

/**
 *
 */
@Config(constants = BuildConfig, sdk = 21, manifest = "src/main/AndroidManifest.xml")
class TakListSpec extends RoboSpecification {

  TaskListActivity activity
  
  void setup() {
    def mockManager = Mock(TaskManager)
    def ourTask = new Task()
    ourTask.id = 1
    ourTask.title = 'Some task'

    mockManager.getTasks() >> Observable.just(Arrays.asList(ourTask))

    activity = Robolectric.setupActivity(TaskListActivity_.class)
    activity.taskManager = mockManager
  }

  def "click on add button"() {
    when: 'User clicks on fab'
    activity.onFab()

    then: 'EditTaskActivity is started with intent action NEW'
    shadowOf(activity).getNextStartedActivity() == EditTaskActivity_.intent(activity)
        .action(EditTaskActivity.ACTION_NEW)
        .get()
  }

  def "open details"() {
    given: 'Setup tasks'
    activity.loadData()


    when: 'User clicks on item'
    activity.@recyclerView.measure(0, 0);
    activity.@recyclerView.layout(0, 0, 100, 10000);
    activity.@recyclerView.getChildAt(0).performClick()

    then:
    shadowOf(activity).getNextStartedActivity() == EditTaskActivity_.intent(activity)
        .action(EditTaskActivity.ACTION_EDIT)
        .taskId(1)
        .get()

  }
}
