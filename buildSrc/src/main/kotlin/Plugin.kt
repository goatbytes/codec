object Plugin {

    object JetBrains {
        const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.JetBrains.gradle}"
        object Dokka {
            const val id = "org.jetbrains.dokka"
            const val gradle = "org.jetbrains.dokka:dokka-gradle-plugin:${Version.dokka}"
        }
    }

    object Android {
        const val gradle = "com.android.tools.build:gradle:${Version.Android.gradle}"
    }

    object MavenPublish {
        const val gradle = "com.vanniktech:gradle-maven-publish-plugin:${Version.mavenPublish}"
    }
}