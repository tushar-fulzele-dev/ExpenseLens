plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.expenselens"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.expenselens"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.1"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:sms-parser"))
    implementation(project(":feature:dashboard"))
}

// TODO(0.2.1): self-signed release signing config lands with Ticket
// 7.1, not here.
