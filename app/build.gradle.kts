import com.neversad.paymentapp.build_logic.convention.implementation

plugins {
    alias(libs.plugins.paymentapp.android.application)
    alias(libs.plugins.paymentapp.android.application.compose)
    alias(libs.plugins.paymentapp.android.hilt)
}

android {
    namespace = "com.neversad.paymentapp"

    defaultConfig {
        applicationId = "com.neversad.paymentapp"
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

}

dependencies {
    implementation(projects.feature.pinpad)
    implementation(projects.feature.receipt)

    implementation(projects.core.data.remote)
    implementation(projects.core.data.local)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.navigation.compose)
}
