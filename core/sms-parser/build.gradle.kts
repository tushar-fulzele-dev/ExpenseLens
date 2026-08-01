plugins {
    id("org.jetbrains.kotlin.jvm")
}

// Plain Kotlin module — parser logic has no Android framework
// dependency so it stays fully unit-testable.
// TODO(0.2.1): add real dependencies once parser implementation lands.
