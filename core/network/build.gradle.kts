import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.recipes.convention.publish)
    alias(libs.plugins.recipes.convention.config)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
        publishLibraryVariants("release")
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
        }
        commonMain.dependencies {
            api(libs.bundles.ktor.core)
            implementation(libs.kotlinx.coroutines.core)
            api(libs.kotlin.logging)
            implementation(libs.koin.core)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "br.com.ricarlo.network"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

buildkonfig {
    packageName = "${project.group}.${project.name}"

    defaultConfigs {
        buildConfigField(STRING, "TIMEOUT", "100", const = true)
    }

    defaultConfigs("uat") {
        buildConfigField(STRING, "TIMEOUT", "1000", const = true)
    }

    targetConfigs {
        create("androidMain") {
            buildConfigField(STRING, "TIMEOUT", "10", const = true)
        }

        create("iosMain") {
            buildConfigField(STRING, "TIMEOUT", "20", const = true)
        }
    }

    val propsFile = project.rootProject.file("local.properties")
    val props = Properties()

    if (propsFile.exists()) {
        propsFile.inputStream().use { props.load(it) }
    }

    defaultConfigs {
        props.entries.forEach {
            val key = it.key.toString().replace(".", "_").uppercase()
            buildConfigField(STRING, key, it.value.toString(), const = true)
        }
    }
}
