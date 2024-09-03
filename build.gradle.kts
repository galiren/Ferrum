// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.diffplug.spotless)
}

spotless {
  kotlin {
    target("**/*.kt")
    // todo remove later
    targetExclude("**/androidTest/**", "**/test/**")
    ktlint().setEditorConfigPath("./.editorconfig")
    trimTrailingWhitespace()
    endWithNewline()
  }
  kotlinGradle {
    target("**/*.gradle.kts")
    ktlint().setEditorConfigPath("./.editorconfig")
    trimTrailingWhitespace()
    endWithNewline()
  }
}
