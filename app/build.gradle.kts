plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    id("kotlin-parcelize")
}

apply("../config.gradle.kts")

android {
    namespace = "${rootProject.extra.get("applicationId")}"
    compileSdk = rootProject.extra.get("compileSdk").toString().toInt()

    defaultConfig {
        applicationId = "${rootProject.extra.get("applicationId")}"
        minSdk = rootProject.extra.get("minSdk").toString().toInt()
        targetSdk = rootProject.extra.get("compileSdk").toString().toInt()
        versionCode = rootProject.extra.get("versionCode").toString().toInt()
        versionName = "${rootProject.extra.get("versionName")}"

        testInstrumentationRunner = "${rootProject.extra.get("testRunner")}"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${rootProject.extra.get("composeVersion")}"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val composeBom = platform(libs.androidx.compose.bom)


    implementation(libs.hilt)
    implementation(libs.hiltNavigationCompose)
    kapt(libs.hiltDaggerCompiler)

    // Compose
    implementation(composeBom)
    implementation(libs.bundles.jetpackCompost)
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.archComponents)

//    modules
    implementation(project(":core:utils"))
    implementation(project(":core:ui"))
    implementation(project(":features:home"))
    implementation(project(":features:recharge"))


    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.bundles.unit.test)
    androidTestImplementation(libs.bundles.ui.test)
    kaptAndroidTest(libs.hiltDaggerCompiler)
}
