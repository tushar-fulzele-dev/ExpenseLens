plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.expenselens.core.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }
}

// TODO(0.2.1): add Room + SQLCipher dependencies once schema (Ticket
// 2.1) lands.
