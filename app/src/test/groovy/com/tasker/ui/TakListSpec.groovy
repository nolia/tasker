package com.tasker.ui

import com.tasker.BuildConfig
import org.robolectric.annotation.Config
import org.robospock.RoboSpecification

/**
 *
 */
@Config(constants = BuildConfig, sdk = 21, manifest = "src/main/AndroidManifest.xml")
class TakListSpec extends RoboSpecification {

}
