package com.ricarlo.recipes

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import java.io.IOException
import java.util.Properties

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.getLocalProperties(
    filename: String = "secrets.properties"
): Map<String, String> {
    val propsFile = rootProject.file(filename)
    val props = Properties()

    if (propsFile.exists()) {
        try {
            propsFile.inputStream().use { props.load(it) }
        } catch (e: IOException) {
            logger.warn("Could not load properties from $filename: ${e.message}")
            // Optionally, rethrow or return empty map depending on desired error handling
            return emptyMap()
        }
    } else {
        logger.info("Properties file $filename not found. No secret properties will be loaded.")
    }

    return props.entries.associate { entry ->
        val key = entry.key.toString().replace(".", "_").uppercase()
        key to entry.value.toString()
    }
}
