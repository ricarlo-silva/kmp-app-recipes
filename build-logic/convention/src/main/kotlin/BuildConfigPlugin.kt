import com.ricarlo.recipes.configureBuildConfig
import com.ricarlo.recipes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class BuildConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        with(pluginManager) {
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
        }
        extensions.configure<KotlinMultiplatformExtension>(::configureBuildConfig)
    }
}
