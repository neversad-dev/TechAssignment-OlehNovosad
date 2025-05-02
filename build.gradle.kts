import org.jmailen.gradle.kotlinter.KotlinterExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kotlinter) apply false


}
buildscript {
    dependencies {
        classpath(libs.compose.rules)
    }
}

subprojects {
    plugins.withId("com.android.application") { applyKotlinter() }
    plugins.withId("com.android.library")     { applyKotlinter() }
}

fun Project.applyKotlinter() {
    // bring plugin on the classpath —
    // this call is safe even if already applied by a convention plugin
    pluginManager.apply(libs.plugins.kotlinter.get().pluginId)

    // optional global configuration
    extensions.configure<KotlinterExtension> {
        // Fail the build on any lint errors
        ktlintVersion = libs.versions.ktlint.get()

        ignoreFormatFailures = false
        ignoreLintFailures = false

        reporters = arrayOf("checkstyle")
    }

    dependencies{

    }


}

