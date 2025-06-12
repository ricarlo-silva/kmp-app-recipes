import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.util.Properties

class BuildConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val propsFile = project.rootProject.file("secrets.properties")
        val props = Properties()

        if (propsFile.exists()) {
            propsFile.inputStream().use { props.load(it) }
        }

        val generatedDir = "generated/src/config"
        val outputDir = project.layout.buildDirectory.dir(generatedDir).get().asFile

        val packageName = "${project.group}.${project.name}"
        val file = outputDir.resolve("$packageName/BuildConfig.kt")
        file.parentFile.mkdirs()
        file.writeText(
            buildString {
                appendLine("package $packageName\n")
                appendLine("internal object BuildConfig {")
                props.entries.forEach {
                    val key = it.key.toString().replace(".", "_").uppercase()
                    appendLine("    const val $key = \"${it.value}\"")
                }
                append("}")
            }
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
