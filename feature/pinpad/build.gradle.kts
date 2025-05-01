plugins {
    alias(libs.plugins.paymentapp.android.feature)
    alias(libs.plugins.paymentapp.android.library.compose)
}

android {
    namespace = "com.neversad.paymentapp.feature.pinpad"
}

dependencies {

    implementation(projects.core.domain)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
}