plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.parallelspace.app.system_kernel.process_control"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
        consumerProguardFiles("$consumerProguardFiles")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
