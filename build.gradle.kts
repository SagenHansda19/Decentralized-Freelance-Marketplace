// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false

}

buildscript {
    dependencies {

        // Add the Firebase Gradle plugin
        classpath("com.google.gms:google-services:4.3.15")

        // Ensure using a compatible version of AGP
        classpath("com.android.tools.build:gradle:8.5.2")

        // Use a compatible version of the Kotlin Gradle plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    }
}

// Define JVM target version for the project (ensure Kotlin and Java target the same version)
allprojects {
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "1.8" // Or "17" if you prefer Java 17
        targetCompatibility = "1.8" // Or "17" if you prefer Java 17
    }

    // Ensure the Kotlin JVM target is compatible with the Java version
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8" // Or "17" if using Java 17
        }
    }
}
