import com.google.protobuf.gradle.*

plugins {
    id("com.google.protobuf")
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    api("javax.annotation:javax.annotation-api:1.3.2")
    api("io.grpc:grpc-kotlin-stub:1.0.0")
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
