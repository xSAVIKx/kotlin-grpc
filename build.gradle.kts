plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.32"
    id("org.jetbrains.kotlin.kapt") version "1.4.32"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "6.1.0" apply false
    id("io.micronaut.application") version "1.4.2" apply false
    id("com.google.protobuf") version "0.8.13" apply false
    id("com.google.cloud.tools.jib") version "2.6.0" apply false
}

version = "0.1"
group = "io.spine"

val kotlinVersion = project.properties.get("kotlinVersion")
val micronautVersion = project.properties.get("micronautVersion")
repositories {
    mavenCentral()
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("org.jetbrains.kotlin.plugin.allopen")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        kapt(enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
        kapt("io.micronaut:micronaut-inject-java")

        implementation(enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
        implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
        implementation("javax.annotation:javax.annotation-api:1.3.2")
    }
    java {
        sourceCompatibility = JavaVersion.toVersion("11")
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
        compileTestKotlin {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
}
