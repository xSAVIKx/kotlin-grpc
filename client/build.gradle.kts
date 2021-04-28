import io.micronaut.gradle.MicronautRuntime
import io.micronaut.gradle.MicronautTestRuntime

plugins {
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
    id("com.google.cloud.tools.jib")
}

micronaut {
    runtime(MicronautRuntime.NETTY)
    testRuntime(MicronautTestRuntime.JUNIT_5)
    processing {
        incremental(true)
        annotations("io.spine.client.*")
    }
}

dependencies {
    implementation(project(":model"))
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.grpc:micronaut-grpc-client-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("org.apache.logging.log4j:log4j-core:2.13.2")
    runtimeOnly("org.apache.logging.log4j:log4j-api:2.13.2")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.13.2")
    compileOnly("org.graalvm.nativeimage:svm")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut.gcp:micronaut-gcp-tracing")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation(project(":server"))
}


application {
    mainClass.set("io.spine.client.Application")
}

tasks {
//
//dockerBuild {
//    images = ["${System.env.DOCKER_IMAGE ?: project.name}:$project.version"]
//}
//
//dockerBuildNative {
//    images = ["${System.env.DOCKER_IMAGE ?: project.name}:$project.version"]
//}

    jib {
        to {
            image = "gcr.io/myapp/jib-image"
        }
    }
}
