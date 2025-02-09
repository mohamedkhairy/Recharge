// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.parcelize) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.serialization.plugin) apply false
    alias(libs.plugins.android.test) apply false

}
buildscript {
    apply("config.gradle.kts")
}
