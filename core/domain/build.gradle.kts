
plugins {
    alias(libs.plugins.paymentapp.android.library)
    alias(libs.plugins.paymentapp.android.hilt)
}

android {
    namespace = "com.neversad.paymentapp.core.domain"
}

dependencies {
    api(projects.core.model)

}
