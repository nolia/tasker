package com.tasker

import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 *
 */
@Config(constants = BuildConfig.class, sdk = 21)
class ListShowSpec extends RoboSpecification {

  def "should compile"() {
    when: 'I compile project with tests'
    println 'Compiling...'

    then: 'It compiles'
    1 == 1
  }
}
