import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.tian.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val localProperties = Properties().apply {
            val localPropertiesFile = rootProject.file("secrets.properties")
            if (localPropertiesFile.exists()) {
                localPropertiesFile.inputStream().use { load(it) }
            }
        }
        buildConfigField("String", "API_KEY", "${localProperties["MOVIE_API_KEY"]}")
        buildConfigField("String", "BASE_URL", "${localProperties["MOVIE_BASE_URL"]}")
        buildConfigField("String", "IMG_URL", "${localProperties["MOVIE_IMG_URL"]}")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.converter.scalars)
    implementation (libs.logging.interceptor)

    implementation(libs.kotlin.reflect)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.hilt.navigation.compose)

    annotationProcessor(libs.hilt.compiler)
}