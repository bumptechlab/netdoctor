import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    //发布到远程仓库需要用到此插件
    id("com.vanniktech.maven.publish") version "0.27.0"
}

android {
    namespace = "com.net.doctor"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("dnsjava:dnsjava:3.5.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
}

publishing {
    repositories {
        maven {
            // $rootDir 表示项目的根目录
            // 这里配置发布到的本地目录
            name = "local"
            setUrl("$rootDir/repo")
        }
    }
}