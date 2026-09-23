plugins {
    application
    checkstyle
    pmd
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

val junitVersion = "5.14.+"
val mockitoVersion = "5.23.+"
// Official fix required for loading of mockito agent; see (1) below
// https://javadoc.io/static/org.mockito/mockito-core/latest/org.mockito/org/mockito/Mockito.html#0.3
val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    implementation("org.jetbrains:annotations:26.0.2")
    // JUnit dependencies
    testImplementation(platform("org.junit:junit-bom:${junitVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Mockito dependencies
    testImplementation(platform("org.mockito:mockito-bom:${mockitoVersion}"))
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.mockito:mockito-core")
    // (1) Add mockito-core to mockitoAgent configuration
    mockitoAgent("org.mockito:mockito-core:${mockitoVersion}") { isTransitive = false }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass = "ch.zhaw.it.pm.teamname.projectname.App"
}

javafx {
    version = "21.0.10"
    modules("javafx.controls", "javafx.fxml")
}

checkstyle {
    toolVersion = "10.12.4"
}

pmd {
    toolVersion = "6.55.0"
}

tasks.test {
    useJUnitPlatform()
    doFirst {
        jvmArgs(
            "-javaagent:${mockitoAgent.singleFile.absolutePath}"
        )
    }
}

tasks.named<Test>("test")

tasks.named<Javadoc>("javadoc") {
    isFailOnError = true
}

tasks.withType<Checkstyle> {
    reports {
        xml.required = true
        html.required = true
    }
    configProperties?.set(
        "checkstyle.suppressions.file",
        file("config/checkstyle/suppressions.xml").absolutePath
    )
}

tasks.withType<Pmd> {
    reports {
        xml.required = true
        html.required = true
    }
}
