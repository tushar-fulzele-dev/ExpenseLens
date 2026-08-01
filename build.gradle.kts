// Root build file. Module-level build.gradle.kts files apply these
// plugins as needed rather than repeating plugin versions.
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("com.android.library") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.24" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.24" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.6" apply false
}

// TODO(0.2.1): flesh out shared detekt/ktlint config once modules have
// real source sets. Kept intentionally minimal so CI can run green on
// a no-op PR before any real code lands.
