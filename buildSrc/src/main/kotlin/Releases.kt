import org.gradle.api.Project

const val DEVELOPER_ID = "goatbytes.io"
const val DEVELOPER_NAME = "goatbytes.io"
const val DEVELOPER_URL = "https://github.com/goatbytes"

const val RELEASE_GROUP = "io.goatbytes"
const val RELEASE_ARTIFACT = "codec"
const val RELEASE_VERSION = "0.1.0"
const val RELEASE_DESCRIPTION = "An open-source codec library for Kotlin Multi-Platform."
const val RELEASE_URL = "https://github.com/$DEVELOPER_ID/$RELEASE_ARTIFACT"

const val LICENSE_NAME = "MIT License"
const val LICENSE_URL = "https://opensource.org/licenses/MIT"
const val LICENSE_DIST = "repo"

const val SCM_CONNECTION = "scm:git:https://github.com/$DEVELOPER_ID/$RELEASE_ARTIFACT.git"
const val SCM_DEV_CONNECTION = "scm:git:ssh://git@github.com/$DEVELOPER_ID/$RELEASE_ARTIFACT.git"

fun Project.isConfiguredForPublishing() = findProperty("mavenCentralUsername") == DEVELOPER_ID
