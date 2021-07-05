plugins {
    kotlin("jvm") version "1.5.10"
    id("groovy")
    id("io.gitlab.arturbosch.detekt") version "1.17.1"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
}

group = "io.github.aplotnikov"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val kotlintestVersion = "4.6.0"

dependencies {
    implementation("io.vavr:vavr:0.10.3")

    testImplementation(platform("org.junit:junit-bom:5.7.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("io.kotest:kotest-framework-api:$kotlintestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotlintestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotlintestVersion")
    testImplementation("io.mockk:mockk:1.12.0")

    testImplementation("org.spockframework:spock-core:2.0-groovy-3.0")
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
