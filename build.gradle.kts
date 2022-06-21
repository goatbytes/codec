buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Plugin.JetBrains.gradle)
        classpath(Plugin.Android.gradle)
        classpath(Plugin.JetBrains.Dokka.gradle)
        classpath(Plugin.MavenPublish.gradle)
    }
}

plugins {
    id(Plugin.JetBrains.Dokka.id) version Version.dokka
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    group = RELEASE_GROUP
    version = RELEASE_VERSION
}
