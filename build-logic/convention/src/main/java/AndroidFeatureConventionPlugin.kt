import com.neversad.paymentapp.build_logic.convention.implementation
import com.neversad.paymentapp.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("paymentapp.android.library")
                apply("paymentapp.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {

                // Define common dependencies for feature modules
                implementation(libs.findLibrary("androidx-navigation-compose").get())
                implementation(libs.findLibrary("kotlinx-serialization-json").get())
                implementation(libs.findLibrary("androidx-hilt-navigation-compose").get())
                implementation(libs.findLibrary("androidx-lifecycle-viewModelCompose").get())
                implementation(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
            }
        }
    }
}
