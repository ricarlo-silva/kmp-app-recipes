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
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("mavenPublish") {
            id = "recipes.convention.publish"
            implementationClass = "MavenPublishConventionPlugin"
        }
        register("codeAnalysis") {
            id = "recipes.convention.analysis"
            implementationClass = "AnalysisConventionPlugin"
        }
    }
}

//gradle.serviceOf<DependenciesAccessors>().classes.asFiles.forEach {
//    compileOnly(files(it.absolutePath))
//}
//val Project.libs
//    get(): LibrariesForLibs = extensionOf(this, "libs") as LibrariesForLibs
//
//fun PluginManager.alias(notation: Provider<PluginDependency>) {
//    apply(notation.get().pluginId)
//}
//fun PluginManager.alias(notation: ProviderConvertible<PluginDependency>) {
//    apply(notation.asProvider().get().pluginId)
//}
