plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.8.10"
}

android {
    namespace = "com.deathsdoor.musicmatch"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

kotlin {
    android()
    jvm()
    js(IR) {
        browser()
        nodejs()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.github.Deaths-Door.Reqeust-Utilities:request-utils :1.0.0")
            }
        }
    }
}