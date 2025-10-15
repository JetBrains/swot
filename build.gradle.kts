group = "swot"
version = "0.1"

plugins {
    kotlin("jvm") version "2.0.20" apply true

    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
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
