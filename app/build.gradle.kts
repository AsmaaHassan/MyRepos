import java.util.Properties
val properties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    properties.load(localPropertiesFile.inputStream())
}
val githubClientId = properties.getProperty("githubClientId", "")
val githubClientSecret = properties.getProperty("githubClientSecret", "")
val githubRedirectUri = properties.getProperty("githubRedirectUri", "")

// 1. Plugins block updated for Hilt and KSP
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android) // Added Hilt plugin
    alias(libs.plugins.ksp)         // Added KSP for annotation processing
//    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.compose.compiler)

}

android {
    namespace = "com.example.github"
    compileSdk = 36 // 2. compileSdk set to a stable version (34)

    defaultConfig {
        applicationId = "com.example.github"
        minSdk = 26 // 3. minSdk lowered to a more practical level (26)
        targetSdk = 36 // targetSdk should match compileSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        android.buildFeatures.buildConfig =true
        buildConfigField("String", "GITHUB_CLIENT_ID", "\"${githubClientId}\"")
        buildConfigField("String", "GITHUB_CLIENT_SECRET", "\"${githubClientSecret}\"")
        buildConfigField("String", "GITHUB_REDIRECT_URI", "\"${githubRedirectUri}\"")

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
        // 4. Java version updated to 17 for modern Android
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    // 5. Compose options block is now required
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.14" // Ensure this version is compatible with your Kotlin version
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// 6. Dependencies block updated for Hilt, Moshi, and corrected aliases
dependencies {
    // Core & Lifecycle
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Compose BOM and Dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.graphics)
    implementation(libs.androidx.compose.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.icons.extended)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Hilt for Dependency Injection
    implementation(libs.hilt.android)
    implementation(libs.androidx.browser)
    ksp(libs.hilt.compiler) // Use ksp() instead of kapt()

    // Network (Retrofit, OkHttp, Moshi)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen) // Use ksp() for Moshi's code generation
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)

    // Coroutines
    implementation(libs.coroutines.android)

    // Security
    implementation(libs.androidx.security.crypto)

    // Testing
    testImplementation(libs.junit)
    // AndroidTest dependencies are not defined in your TOML, add them if needed
    // androidTestImplementation(libs.androidx.junit)
    // androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.manifest)

    // Debug
    debugImplementation(libs.androidx.compose.tooling.preview)

    val room_version = "2.8.4"

    implementation("androidx.room:room-runtime:$room_version")

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:${room_version}")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:${room_version}")
}

