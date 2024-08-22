apply {
    from("$rootDir/android-library-build.gradle")
}

plugins {
    alias(libs.plugins.android.library)
    id("kotlinx-serialization")
}

android {
    namespace = "${rootProject.extra.get("applicationId")}.feature.home"
}

dependencies {

    val composeBom = platform(libs.androidx.compose.bom)

    "implementation"(composeBom)
    "implementation"(libs.serialization)
    "implementation"(libs.bundles.jetpackCompost)
    "implementation"(libs.bundles.archComponents)
    "implementation"(libs.bundles.kotlinCoroutines)

    "implementation"(project(":core:sharedData"))
    "implementation"(project(":core:utils"))
    "implementation"(project(":core:ui"))

    "testImplementation"(libs.bundles.unit.test)
    "androidTestImplementation"(libs.bundles.ui.test)
    "kaptAndroidTest"(libs.hiltDaggerCompiler)


}
