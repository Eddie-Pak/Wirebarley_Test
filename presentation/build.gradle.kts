plugins {
    alias(libs.plugins.wirebarley.android.library)
    alias(libs.plugins.wirebarley.android.compose)
    alias(libs.plugins.wirebarley.android.hilt)
}

android {
    namespace = "com.wirebarley.presentation"
}

dependencies {
    implementation(project(":domain"))
}