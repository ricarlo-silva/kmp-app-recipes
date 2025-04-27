
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MavenPublishConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("maven-publish")
            }

            version = rootProject.file("./config/VERSION_NAME").readText().trim()
            group = "com.ricarlo"

            afterEvaluate {
                extensions.getByType(KotlinMultiplatformExtension::class.java).apply {
                    androidTarget {
                        publishLibraryVariants("release")
                    }
                }
                extensions.getByType(PublishingExtension::class.java).apply {
                    publications {
//                        create("release", MavenPublication::class.java) {
//                            from(components.getByName("release"))
//                            groupId = "com.ricarlo"
//                            artifactId = name
//                            version = "1.0.0"
//
//                            pom {
//                            name.set("$name Multiplatform library")
//                            description.set("$name Multiplatform library")
//                            url.set("https://github.com/ricarlo-silva/kmp-app-recipes")
//
//                            licenses {
//                                license {
//                                    name.set("MIT")
//                                    url.set("https://opensource.org/licenses/MIT")
//                                }
//                            }
//                            developers {
//                                developer {
//                                    id.set("ricarlo-silva")
//                                    name.set("Ricarlo Silva")
//                                    email.set("ricarlosilv@gmail.com")
//                                }
//                            }
//                        }
                    }
                    repositories {
                        maven {
                            name = "GitHubPackages"
                            url = uri("https://maven.pkg.github.com/ricarlo-silva/kmp-app-recipes")
                            credentials {
                                username = System.getenv("GITHUB_ACTOR")
                                password = System.getenv("GITHUB_TOKEN")
                            }
                        }
                    }
                }
            }
        }
    }
}
