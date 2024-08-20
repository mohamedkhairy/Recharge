apply {
    from("$rootDir/android-library-build.gradle")
}

plugins {
    alias(libs.plugins.ksp)
}


dependencies {

    val composeBom = platform(libs.androidx.compose.bom)
    "implementation"(composeBom)
    "implementation"(libs.bundles.jetpackCompost)
    "implementation"(libs.hiltNavigationCompose)
    "implementation"(libs.bundles.archComponents)
    "implementation"(libs.bundles.kotlinCoroutines)
    "implementation"(project(":core:utils"))
    "implementation"(project(":core:ui"))
    "implementation"(project(":core:sharedData"))



}
