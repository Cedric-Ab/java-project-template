plugins {
    application
    checkstyle
    pmd
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

checkstyle {
    toolVersion = "10.12.4"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
pmd {
    toolVersion = "6.55.0"
}

    useJUnitPlatform()
tasks.named<Test>("test")

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
