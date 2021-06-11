pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.jvm") version "1.4.32"
        id("org.jetbrains.kotlin.kapt") version "1.4.32"
        id("org.jetbrains.kotlin.plugin.allopen") version "1.4.32"
        id("com.github.johnrengelman.shadow") version "6.1.0"
        id("io.micronaut.application") version "1.4.2"
        id("com.google.protobuf") version "0.8.16"
        id("com.google.cloud.tools.jib") version "2.6.0"
        id("com.google.cloud.tools.appengine-appyaml") version "2.4.1"
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.google.cloud.tools.appengine")) {
                useModule("com.google.cloud.tools:appengine-gradle-plugin:2.4.1")
            }
        }
    }
}
rootProject.name = "kotlin-grpc"
include("model")
include("server")
include("client")
