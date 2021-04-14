package io.spine.server

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.spine.model.GreeterGrpcKt
import io.spine.model.HelloRequest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@DisplayName("`Greeter` should")
@MicronautTest
class KotlinGrpcTest {

    @Inject
    lateinit var greetingClient: GreeterGrpcKt.GreeterCoroutineStub

    @Test
    fun `say hello to John in response to the client request`() = runBlocking {
        Assertions.assertEquals(
            "Hello John!",
            greetingClient.sayHello(
                HelloRequest.newBuilder().setName("John").build()
            ).message
        )
    }

//    @Test
//    fun `send multiple messages to the client`() = runBlocking {
//        greetingClient.serverTalking(
//            HelloRequest.newBuilder().setName("Face Dancer").build()
//        ).collect()
//        Assertions.assertEquals(
//            "Bonjour! My name is Greeter.",
//            greetingClient.serverTalking()
//        )
//    }
}

@Factory
class Clients {
    @Singleton
    fun greetingClient(
        @Named(GrpcServerChannel.NAME) @GrpcChannel(GrpcServerChannel.NAME)
        channel: ManagedChannel
    ): GreeterGrpcKt.GreeterCoroutineStub {
        return GreeterGrpcKt.GreeterCoroutineStub(
            channel
        )
    }
}
