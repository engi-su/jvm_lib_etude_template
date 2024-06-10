package components.sqa

import com.diffplug.gradle.spotless.SpotlessExtension
import components.libs
import extensions.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class SpotlessLintPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("spotless".plugin(libs))
            val extension = extensions.getByType<SpotlessExtension>()
            when {
                isJvmLib() -> configureSpotless(extension)
                else -> throw UnsupportedOperationException("Jvm library plugin is missed")
            }
        }
    }

    private fun Project.configureSpotless(extension: SpotlessExtension) {
        configureSpotlessBase(extension)
        extension.apply {
            kotlin {
                ktlint("ktlint".version(libs))
                    .setEditorConfigPath("$rootDir/config/.editorconfig")
                    .editorConfigOverride(
                        mapOf(
                            "ktlint_code_style" to "intellij_idea"
                        )
                    )
            }
        }
    }

    private fun Project.configureSpotlessBase(extension: SpotlessExtension) = extension.apply {
        java {
            target("src/*/java/**/*.java")
            googleJavaFormat().aosp()
            removeUnusedImports()
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }

        kotlin {
            target("src/*/kotlin/**/*.kt")
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }

        kotlinGradle {
            target("**/*.gradle.kts")
            ktlint("ktlint".version(libs))
        }

        // Don't add spotless as dependency for the Gradle's check task to facilitate separated codebase checks
        isEnforceCheck = false
    }

}

