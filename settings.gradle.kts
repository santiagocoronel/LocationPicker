pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
    }
    repositories {
        google ()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LocationPicker"

include(":app")
include(":core")
include(":core:core-ui")
include(":core:core-network")
include(":core:core-local")
include(":core:core-datebase-model")
include(":core:core-domain")
include(":core:core-utils")

include(":feature-locationpicker")
include(":feature-locationpicker:feature-locationpicker-ui")
include(":feature-locationpicker:feature-locationpicker-presentation")
include(":feature-locationpicker:feature-locationpicker-domain")
include(":feature-locationpicker:feature-locationpicker-data")
include(":feature-locationpicker:feature-locationpicker-local")
include(":feature-locationpicker:feature-locationpicker-remote")
