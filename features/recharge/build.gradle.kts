apply {
    from("$rootDir/android-library-build.gradle")
}

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "${rootProject.extra.get("applicationId")}.feature.recharge"

    defaultConfig {
        testInstrumentationRunner = "com.example.recharge.feature.recharge.runner.CustomTestRunner"
    }
}

dependencies {

    val composeBom = platform(libs.androidx.compose.bom)
    "implementation"(composeBom)
    "implementation"(libs.bundles.jetpackCompost)
    "implementation"(libs.bundles.archComponents)
    "implementation"(libs.bundles.kotlinCoroutines)
    "implementation"(project(":core:utils"))
    "implementation"(project(":core:ui"))
    "implementation"(project(":core:sharedData"))

    "debugImplementation"(libs.androidx.ui.test.manifest)
    "testImplementation"(libs.bundles.unit.test)
    "androidTestImplementation"(libs.bundles.ui.test)
    "kaptAndroidTest"(libs.hiltDaggerCompiler)

}
