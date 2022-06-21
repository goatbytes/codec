import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("com.android.library")
    id("com.vanniktech.maven.publish")
}

kotlin {
    android()

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = RELEASE_ARTIFACT
            xcf.add(this)
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 28
        targetSdk = 32
    }
}

if (project.isConfiguredForPublishing()) {
    mavenPublishing {
        publishToMavenCentral(SonatypeHost.S01)
        signAllPublications()

        pom {
            name.set(project.name)
            description.set(RELEASE_DESCRIPTION)
            url.set(RELEASE_URL)
            licenses {
                license {
                    name.set(LICENSE_NAME)
                    url.set(LICENSE_URL)
                    distribution.set(LICENSE_DIST)
                }
            }
            scm {
                connection.set(SCM_CONNECTION)
                developerConnection.set(SCM_DEV_CONNECTION)
                url.set(RELEASE_URL)
            }
            developers {
                developer {
                    id.set(DEVELOPER_ID)
                    name.set(DEVELOPER_NAME)
                    url.set(DEVELOPER_URL)
                }
            }
        }
    }
}
