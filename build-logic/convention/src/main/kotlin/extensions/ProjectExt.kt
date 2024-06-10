package extensions

import org.gradle.api.Project

internal fun Project.isJvmLib() = pluginManager.hasPlugin("java-library")
