pluginManagement {
    repositories {
        maven{
            setUrl("https://repo.huaweicloud.com/repository/maven/")
        }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven{
            setUrl("https://repo.huaweicloud.com/repository/maven/")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "My Application"
include(":app")
 