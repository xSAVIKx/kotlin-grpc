package io.spine.client

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.spine.GreeterGrpc

import javax.inject.Singleton


@Factory
class Clients {
    @Singleton
    fun reactiveStub(
        @GrpcChannel("greeter") channel: ManagedChannel?
    ): GreeterGrpc.GreeterStub {
        return GreeterGrpc.newStub(channel)
    }
}
