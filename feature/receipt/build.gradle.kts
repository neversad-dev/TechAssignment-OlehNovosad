import com.neversad.paymentapp.build_logic.convention.implementation

plugins {
    alias(libs.plugins.paymentapp.android.library.compose)
    alias(libs.plugins.paymentapp.android.feature)
}

android {
    namespace = "com.neversad.paymentapp.feature.receipt"

}

dependencies {
    implementation(projects.core.ui)

    implementation(projects.core.domain)


}