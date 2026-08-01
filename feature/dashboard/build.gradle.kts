plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.expenselens.feature.dashboard"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    buildFeatures {
        compose = true
    }
}

dependencies {   // ADD entire block — didn't exist before
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-android-compiler:2.51.1")
}

// TODO(0.2.1 / 4.1): add Compose BOM + UI dependencies once design
// system (Ticket 4.1) lands.
