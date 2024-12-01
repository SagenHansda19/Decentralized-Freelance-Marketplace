plugins {
    id("com.android.application") version "8.5.2"
    id("org.jetbrains.kotlin.android") version "1.9.0"
    id("com.google.gms.google-services") // Required for Firebase

}

android {
    namespace = "com.example.decentralizedfreelancemarketplace"

    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.decentralizedfreelancemarketplace"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Or VERSION_17 if using Java 17
        targetCompatibility = JavaVersion.VERSION_1_8 // Or VERSION_17 if using Java 17
    }

    kotlinOptions {
        jvmTarget = "1.8" // Or "17" if using Java 17
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    // Firebase
    implementation("com.google.firebase:firebase-auth-ktx:22.1.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.6.0")
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))

    // Jetpack Compose
    implementation("androidx.compose.material:material-icons-extended:1.5.1")
    implementation("androidx.compose.material3:material3:1.1.1") // Material3
    implementation("androidx.compose.ui:ui:1.5.1") // Jetpack Compose UI
    implementation("androidx.compose.material:material:1.5.1") // Material Design Components
    implementation("androidx.navigation:navigation-compose:2.8.4") // Navigation Compose
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1") // Lifecycle support

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
