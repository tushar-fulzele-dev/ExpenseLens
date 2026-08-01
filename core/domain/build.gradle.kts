plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

// Plain Kotlin module — no Android framework dependency by design.
// TODO(0.2.1): add real dependencies once use cases exist.
