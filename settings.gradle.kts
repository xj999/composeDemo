pluginManagement {
    repositories {
        maven {
            url =uri("https://maven.aliyun.com/repository/public/")
        }
        maven {
            url =uri("https://maven.aliyun.com/repository/central")
        }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url =uri("https://maven.aliyun.com/repository/public/")
        }
        maven {
            url =uri("https://maven.aliyun.com/repository/central")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "My Application"
include(":app")
 