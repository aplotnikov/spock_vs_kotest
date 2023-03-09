plugins {
    kotlin("jvm") version "1.8.10"
    groovy
    codenarc
    id("io.gitlab.arturbosch.detekt") version "1.18.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

group = "io.github.aplotnikov"
version = "1.0-SNAPSHOT"
java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

repositories {
    mavenCentral()
}

val kotestVersion = "5.5.5"

dependencies {
    implementation("io.vavr:vavr:0.10.4")

    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.24.2")

    testImplementation("io.kotest:kotest-framework-api:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("org.apache.commons:commons-lang3:3.12.0")
    testImplementation("org.awaitility:awaitility:4.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

    testImplementation("org.spockframework:spock-core:2.3-groovy-3.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = java.sourceCompatibility.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<CodeNarc> {
    version = "3.2.0"
    configFile = rootProject.file("config/codenarc/codenarc.groovy")
}
