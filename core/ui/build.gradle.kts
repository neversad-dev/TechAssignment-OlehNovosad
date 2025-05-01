import com.neversad.paymentapp.build_logic.convention.implementation

plugins {
    alias(libs.plugins.paymentapp.android.library)
    alias(libs.plugins.paymentapp.android.library.compose)

}

android {
    namespace = "com.neversad.paymentapp.core.ui"
}

dependencies {
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.text.googlefonts)
}
