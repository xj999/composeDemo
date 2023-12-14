plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")

}
android {
    namespace = "com.yuexun.myapplication"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    defaultConfig {
        applicationId = "com.yuexun.myapplication"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("../lapp.jks")
            storePassword = "498002079"
            keyAlias = "lapp"
            keyPassword = "498002079"
        }

        create("release") {
            storeFile = file("../lapp.jks")
            storePassword = "498002079"
            keyAlias = "lapp"
            keyPassword = "498002079"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.bundles.activity)
    implementation(libs.bundles.compose)

    implementation(project(mapOf("path" to ":midai-data")))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation(libs.bundles.okhttp)
    implementation(libs.timber)
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.gson)
    implementation (libs.mmkv)

}