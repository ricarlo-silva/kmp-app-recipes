import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.util.Properties

class BuildConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val propsFile = project.rootProject.file("local.properties")
        val props = Properties()

        if (propsFile.exists()) {
            propsFile.inputStream().use { props.load(it) }
        }

        val generatedDir = "generated/src/config"
        val outputDir = project.layout.buildDirectory.dir(generatedDir).get().asFile

        val baseUrl = props["BASE_URL"]?.toString().orEmpty()
        val apiKey = props["API_KEY"]?.toString().orEmpty()

        val file = outputDir.resolve("br.com.ricarlo.${project.name}/BuildConfig.kt")
        file.parentFile.mkdirs()
        file.writeText(
            """
                    package br.com.ricarlo.${project.name}
                    
                    object BuildConfig {
                        const val BASE_URL = "$baseUrl"
                        const val API_KEY = "$apiKey"
                    }
                    """.trimIndent()
        )

        project.plugins.withId("org.jetbrains.kotlin.multiplatform") {
            project.extensions.configure<KotlinMultiplatformExtension>("kotlin") {
                sourceSets.named("commonMain") {
                    kotlin.srcDir("build/$generatedDir")
                }
            }
        }
    }
}
