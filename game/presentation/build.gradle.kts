import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.jime.game.presentation"
    compileSdk = Project.compileSdk

    defaultConfig {
        minSdk = Project.minSdk
        targetSdk = Project.compileSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    viewBinding {
        enable = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(Modules.gameDomain))

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    implementation(AndroidX.core)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.activity)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.viewModel)
    implementation(AndroidX.runtime)

    implementation(Material.material)

    testImplementation(Testing.junit)
    androidTestImplementation(Testing.androidJunit)

    testImplementation(Coroutine.testing)
    testImplementation(Testing.archCore)

    testImplementation("io.mockk:mockk:1.12.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}