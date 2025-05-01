import com.neversad.paymentapp.build_logic.convention.implementation

plugins {
    alias(libs.plugins.paymentapp.android.library)
    alias(libs.plugins.paymentapp.android.hilt)
}

android {
    namespace = "com.neversad.paymentapp.core.data.local"
}

dependencies {
    implementation(projects.core.domain)

}
