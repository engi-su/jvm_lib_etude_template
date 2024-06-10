package components

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import extensions.bundle
import extensions.isJvmLib

class TestPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            when {
                isJvmLib() -> configureJvmBase()
                else -> throw UnsupportedOperationException("Android or Jvm library or application plugin is missed")
            }
            dependencies {
                add("testImplementation", "kotest".bundle(libs))
            }
        }
    }

    private fun Project.configureJvmBase() {
        tasks.withType<Test>().configureEach{
            useJUnitPlatform()
        }
    }
}


