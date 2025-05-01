import com.neversad.paymentapp.build_logic.convention.implementation

plugins {
    alias(libs.plugins.paymentapp.android.feature)
    alias(libs.plugins.paymentapp.android.library.compose)
}

android {
    namespace = "com.neversad.paymentapp.feature.pinpad"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.domain)

}