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

// TODO(0.2.1 / 4.1): add Compose BOM + UI dependencies once design
// system (Ticket 4.1) lands.
