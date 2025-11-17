
// Top-level build file in your project's root directory

plugins {
    // The alias from your TOML is "hilt-android", not "hilt.android"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt.android) apply false // Use the correct alias "hilt-android"
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
}
