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
        //maven快照
        maven { setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
    }
}

rootProject.name = "netdoctor"
include(":app")
include(":netdoctor")
