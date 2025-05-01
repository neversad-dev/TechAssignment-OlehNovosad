
plugins {
    alias(libs.plugins.paymentapp.android.library)
    alias(libs.plugins.paymentapp.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.neversad.paymentapp.core.data.remote"
}

dependencies {
    implementation(projects.core.domain)

}
