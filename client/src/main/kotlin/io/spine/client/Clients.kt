package io.spine.client

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.spine.model.GreeterGrpc
import javax.inject.Singleton


@Factory
class Clients {

    @Singleton
    fun greeterClientStub(
        @GrpcChannel("greeter") channel: ManagedChannel
    ): GreeterGrpc.GreeterStub {
        return GreeterGrpc.newStub(channel)
    }
}
