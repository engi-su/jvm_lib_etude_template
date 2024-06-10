package components

import components.sqa.DetektLintPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import extensions.library
import extensions.plugin

class SQAPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("android-native-lint".plugin(libs))

            apply<DetektLintPlugin>()
            pluginManager.apply("internal.spotless")
            dependencies {
                "testImplementation"("konsist".library(libs))
            }
        }
    }
}
