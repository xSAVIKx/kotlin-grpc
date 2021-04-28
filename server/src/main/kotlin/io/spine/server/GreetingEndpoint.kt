package io.spine.server

import io.spine.model.GreeterGrpcKt
import io.spine.model.HelloReply
import io.spine.model.HelloRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import javax.inject.Singleton

@Singleton
class GreetingEndpoint : GreeterGrpcKt.GreeterCoroutineImplBase() {

    override suspend fun sayHello(request: HelloRequest): HelloReply {
        println("Client said: $request")
        val message = "Hello ${request.name}!"
        return HelloReply.newBuilder()
            .setMessage(message)
            .build()
    }

    override fun serverTalking(request: HelloRequest): Flow<HelloReply> = flow {
        println("Client said: $request")
        for (i in 1..10) {
            val reply = HelloReply.newBuilder()
                .setMessage("Hello, ${request.name} #${i}.")
                .build()
            println("Saying: $reply")
            emit(reply)
            delay(1000)
        }
    }

    override suspend fun clientTalking(requests: Flow<HelloRequest>): HelloReply {
        println("Client is talking!")
        val clientRequests = requests.toList()
        clientRequests.forEach { print("Client said: $it") }
        return HelloReply.newBuilder()
            .setMessage("Received ${clientRequests.size} requests. Thx!")
            .build()
    }

    override fun talkingTogether(requests: Flow<HelloRequest>): Flow<HelloReply> = flow {
        println("Talking together!")
        requests.collect {
            delay(1000)
            println("Client said: $it")
            val reply = HelloReply.newBuilder()
                .setMessage("Hello, ${it.name}.")
                .build()
            println("Server replied: $reply")
            emit(reply)
        }
    }
}
