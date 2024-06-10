import components.libs
import extensions.asString
import extensions.library
import extensions.version

plugins{
    id("com.diffplug.spotless")
}

spotless {
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
        ktlint("ktlint".version(libs))
            .setEditorConfigPath("$rootDir/config/.editorconfig")
            .editorConfigOverride(
                mapOf(
                    "ktlint_code_style" to "intellij_idea"
                )
            )
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktlint("ktlint".version(libs))
    }

    //Don't add spotless as dependency for the Gradle's check task to facilitate separated codebase checks
    isEnforceCheck = false
}
