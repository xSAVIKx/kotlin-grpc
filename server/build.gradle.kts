import com.google.protobuf.gradle.*
plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
    id("com.google.protobuf")
    id("com.google.cloud.tools.jib")
}

version = "0.1"
group = "io.spine"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
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
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.grpc:micronaut-grpc-server-runtime")
    implementation("io.micronaut.grpc:micronaut-grpc-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("org.apache.logging.log4j:log4j-core:2.13.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
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
    mainClass.set("io.spine.ApplicationKt")
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
sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/java")
            srcDirs("build/generated/source/proto/main/grpckt")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.15.6"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.36.1"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.0.0:jdk7@jar"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without options.
                id("grpc")
                id("grpckt")
            }
        }
    }
}
