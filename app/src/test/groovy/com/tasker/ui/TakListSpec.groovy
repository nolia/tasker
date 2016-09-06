package com.tasker.ui

import android.view.View
import com.tasker.R
import com.tasker.bean.TaskManager
import com.tasker.model.Task
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification
import rx.Observable
import spock.lang.Narrative

import static org.robolectric.Shadows.shadowOf
import static org.robolectric.shadows.ShadowView.clickOn

/**
 *
 */
@Narrative("""
As a user
I can see my task list
""")
@Config(sdk = 21, manifest = "src/main/AndroidManifest.xml")
class TakListSpec extends RoboSpecification {

  TaskListActivity activity
  TaskManager mockManager

  void setup() {
    mockManager = Mock(TaskManager)
    def ourTask = new Task()
    ourTask.id = 1
    ourTask.title = 'Some task'

    this.mockManager.getTasks() >> Observable.just(Arrays.asList(ourTask))

    activity = Robolectric.setupActivity(TaskListActivity_.class)
    activity.taskManager = this.mockManager
  }


  def "click on add button"() {
    when: 'User clicks on fab'
    activity.onFab()

    then: 'EditTaskActivity is started with intent action NEW'
    shadowOf(activity).getNextStartedActivity() == EditTaskActivity_.intent(activity)
        .action(EditTaskActivity.ACTION_NEW)
        .get()
  }

  def "open add task screen"() {
    given: 'I am a user'

    when: 'I click on new task button'
    clickOn(activity.findViewById(R.id.fab))

    then: "I am taken to 'Add Task' screen"
    def intent = shadowOf(activity).getNextStartedActivity()
    intent == EditTaskActivity_.intent(activity)
        .action(EditTaskActivity.ACTION_NEW)
        .get()

    def editTaskActivity = Robolectric.buildActivity(EditTaskActivity_.class).withIntent(intent)
        .create().resume().get()

    and: "I can see input forms for task title and description"
    editTaskActivity.findViewById(R.id.taskTitle).getVisibility() == View.VISIBLE
    editTaskActivity.findViewById(R.id.taskDescription).getVisibility() == View.VISIBLE

    and: 'I cannot see task state'
    editTaskActivity.findViewById(R.id.taskStateLayout).getVisibility() != View.VISIBLE
  }

}
