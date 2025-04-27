plugins {
    `kotlin-dsl`
}

group = "br.com.ricarlo.build_logic.convention"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("mavenPublish") {
            id = "recipes.convention.publish"
            implementationClass = "MavenPublishConventionPlugin"
        }
    }
}
