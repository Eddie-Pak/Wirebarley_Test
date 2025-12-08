plugins {
    alias(libs.plugins.wirebarley.android.library)
    alias(libs.plugins.wirebarley.android.hilt)
    alias(libs.plugins.wirebarley.android.network)
}

android {
    namespace = "com.wirebarley.data"
}

dependencies {
    implementation(project(":domain"))
}