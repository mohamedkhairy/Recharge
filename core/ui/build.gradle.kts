apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {

    val composeBom = platform(libs.androidx.compose.bom)
    "implementation"(composeBom)
    "implementation"(libs.bundles.jetpackCompost)

}
