plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.transact.assessment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.transact.assessment"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(libs.appcompat)
    implementation(libs.activity.compose)
    implementation(libs.koin)
    implementation(libs.koin.androidx.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.paging.compose)
    implementation(libs.paging3)
    implementation(libs.bumptech.glide)
    implementation(libs.glide.compose)
    ksp(libs.glide.annotation)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material.component)
    implementation(libs.navigation.compose)
    implementation(libs.squareup.retrofit)
    implementation(libs.gson)
    implementation(libs.squareup.okhttp.logging)
    implementation(libs.room)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)
}