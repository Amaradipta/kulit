plugins {
    id("com.android.application") version "8.6.0" // using your version reference
    id("org.jetbrains.kotlin.android") version "1.9.0" // using your Kotlin version reference
}

android {
    namespace = "com.example.kulit"
    compileSdk = 35 // Update to your desired compile SDK version

    defaultConfig {
        applicationId = "com.example.kulit"
        minSdk = 24 // If you need to support older devices, keep this
        targetSdk = 35 // Update targetSdk to 35 to match compileSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Set to true if you're using ProGuard or R8 to shrink code
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8 // Ensure compatibility with Java 8 features
    }

    kotlinOptions {
        jvmTarget = "1.8" // Required for Kotlin to ensure JVM 8 bytecode compatibility
    }

    buildFeatures {
        viewBinding = true // Enabling ViewBinding for easier view management
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.espresso:espresso-core:3.6.1")
}
