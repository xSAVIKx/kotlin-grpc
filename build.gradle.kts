plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.32" apply false
    id("org.jetbrains.kotlin.kapt") version "1.4.32" apply false
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.32" apply false
    id("com.github.johnrengelman.shadow") version "6.1.0" apply false
    id("io.micronaut.application") version "1.4.2" apply false
    id("com.google.protobuf") version "0.8.13" apply false
    id("com.google.cloud.tools.jib") version "2.6.0" apply false
}

version = "0.1"
group = "io.spine"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

subprojects {
    apply {
        plugin("java-library")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("org.jetbrains.kotlin.plugin.allopen")
    }
}
