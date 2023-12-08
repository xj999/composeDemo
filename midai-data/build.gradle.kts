plugins {
    id("ivy.feature")
    id("ivy.room")
}

android {
    namespace = "midai.data"
}
kotlin {
    sourceSets.all {
        kotlin.srcDir("build/generated/ksp/$name/kotlin")
    }
}
dependencies {
    implementation(libs.bundles.okhttp)
    implementation (libs.javafaker)
    implementation(libs.gson)
}