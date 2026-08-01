pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ExpenseLens"

include(":app")
include(":core:data")
include(":core:domain")
include(":core:sms-parser")
include(":feature:dashboard")
