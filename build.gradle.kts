// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("ivy.detekt")

    alias(libs.plugins.gradleWrapperUpgrade)

    alias(libs.plugins.koverPlugin)

}
subprojects {
    apply(plugin = "org.jetbrains.kotlinx.kover")
    koverReport {
        // filters for all report types of all build variants
        filters {
            excludes {
                classes(
                    "*Activity",
                    "*Activity\$*",
                    "*.BuildConfig",
                    "dagger.hilt.*",
                    "hilt_aggregated_deps.*",
                    "*.Hilt_*"
                )
                annotatedBy("@Composable")
            }
        }
    }
}
//fixme 不知道以下代码干嘛用的
//wrapperUpgrade {
//    gradle {
//        create("ivyWallet") {
//            repo.set("Ivy-Apps/ivy-wallet")
//            baseBranch.set("main")
//        }
//    }
//}
