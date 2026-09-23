plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "projectName"

fun resolveCheckstylePluginConfig() {
    val inputFile = file(".idea/checkstyle-idea-unresolved.xml")
    val outputFile = file(".idea/checkstyle-idea.xml")

    if (inputFile.exists()) {
        val text = inputFile.readText(Charsets.UTF_8)

        val replaced = text.replace("\${rootDir}", rootDir.absolutePath)

        outputFile.writeText(replaced, Charsets.UTF_8)
    }
}

resolveCheckstylePluginConfig()
