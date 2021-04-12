import com.google.protobuf.gradle.*
plugins {
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
    id("com.google.cloud.tools.jib")
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.spine.*")
    }
}

dependencies {
    implementation(project(":model"))
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.grpc:micronaut-grpc-server-runtime")
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
}


application {
    mainClass.set("io.spine.server.ApplicationKt")
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
