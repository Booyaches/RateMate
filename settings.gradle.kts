pluginManagement {
    repositories {
        google()
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

rootProject.name = "RateMate"
include(":app")
include(":core:database")
include(":core:network")
include(":core:domain")
include(":core:designsystem")
include(":feature:currency")
include(":core:data")
include(":feature:details")
