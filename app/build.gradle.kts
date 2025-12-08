plugins {
    alias(libs.plugins.wirebarley.android.application)
    alias(libs.plugins.wirebarley.android.hilt)
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "com.wirebarley.app"

    defaultConfig {
        applicationId = "com.wirebarley.app"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

secrets {
    propertiesFileName = "local.properties"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))
}