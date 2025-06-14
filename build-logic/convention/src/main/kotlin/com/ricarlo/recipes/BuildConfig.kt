package com.ricarlo.recipes

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private const val KEY_ENV = "ENV"
private const val GENERATED_DIR = "generated/src/commonMain/kotlin"

internal fun Project.configureBuildConfig(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    val outputDir = layout.buildDirectory.dir(GENERATED_DIR).get().asFile

    val packageName = "${group}.${name}"
    val file = outputDir.resolve("$packageName/BuildConfig.kt")
    val properties = getLocalProperties()
    val env = properties[KEY_ENV] ?: Env.UAT.name

    file.parentFile.mkdirs()
    file.writeText(
        buildString {
            appendLine("package $packageName\n")
            appendLine("internal object BuildConfig {")
            properties.filter { (key, _) ->
                !Env.values().any { otherEnv ->
                    key.startsWith("${otherEnv.name}_") && otherEnv.name != env
                }
            }.forEach {
                val key = it.key.removePrefix("${env}_")
                appendLine("    const val $key = \"${it.value}\"")
            }
            append("}")
        }
    )

    sourceSets.named("commonMain") {
        kotlin.srcDir("build/$GENERATED_DIR")
    }
}

enum class Env {
    PRD,
    UAT
}
