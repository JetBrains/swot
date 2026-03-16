group = "swot"
version = "0.1"

plugins {
    kotlin("jvm") version "2.0.20"
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit", "junit", "4.13.2")
}

application {
    mainClass.set("swot.CompilerKt")
}

tasks.withType<Test> {
    useJUnit()

    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.register<JavaExec>("validate") {
    description = "Validate domain .txt files"
    group = "verification"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("swot.ValidatorKt")
    args = providers.gradleProperty("validateFiles").map { it.split(",") }.getOrElse(emptyList())
}
