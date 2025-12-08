plugins {
    `kotlin-dsl`
}

group = "com.wirebarley.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "wirebarley.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "wirebarley.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "wirebarley.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "wirebarley.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidNetwork") {
            id = "wirebarley.android.network"
            implementationClass = "AndroidNetworkConventionPlugin"
        }
        register("jvmLibrary") {
            id = "wirebarley.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}