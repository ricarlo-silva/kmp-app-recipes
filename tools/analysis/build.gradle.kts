import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    compileOnly(libs.lint.api)
    testImplementation(libs.lint.checks)
    testImplementation(libs.lint.tests)

    compileOnly(libs.detekt.api)
    testImplementation(libs.detekt.test)

    testImplementation(kotlin("test"))
}
