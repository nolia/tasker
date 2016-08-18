# Stories list


## User task list
As a user
I can see my task list

Given I am a user
And I have 1 (2, 3, 4) saved task(s) in my list
When I open 'Task List' screen
I can see my 1 (2, 3, 4) tasks with its (their) info on screen

## Adding task
As a user
I should be able to add new tasks

## Open add task screen
Given an empty task list
When I click on 'new task' button
Then I am taken to 'Add Task' screen
And I can see input forms for task title and description
And I cannot see task state

## Save new task from add task screen
Given I'm on 'Add Task' screen
When I input task data
And click on 'Save' button
Then I go back to 'Task List' screen
And I can see my task in the list
And it has state 'New'

## Saving task data should be validated
Given I'm on 'Add Task' screen
When I leave field 'Title' blank
And click on 'Save' button
Then device vibrates 
And 'Title' field is animated to show error and has focus
 
## Changing task
As a user
I should be able to change my task title, description and state
Also I should be able to change tasks state from task list

## Deleting task
As a user 
I should be able to delete a task

Given I have one task in a list
When I open task details
And click on 'Delete' button
Then I'm taken back to 'Task List' screen
And I can no longer see my task in the list


## Open edit task screen
Given I have a task in my list
When I click on task in list
Then I'm taken to 'Edit Task' screen
And I can see task info: title, description and state


## Save edited task
Given I opened 'Edit task screen'
And I changed description and title of the task
And I click 'Save' button
Then I'm taken back to 'Task List' screen
And I can see my task title and description has been updated