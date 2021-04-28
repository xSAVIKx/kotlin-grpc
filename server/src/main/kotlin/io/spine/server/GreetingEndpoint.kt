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
        val message = "Hello ${request.name}!"
        return HelloReply.newBuilder()
            .setMessage(message)
            .build()
    }

    override fun serverTalking(request: HelloRequest): Flow<HelloReply> = flow {
        for (i in 1..10) {
            delay(1000)
            val reply = HelloReply.newBuilder()
                .setMessage("Hello, ${request.name} #${i}.")
                .build()
            emit(reply)
        }
    }

    override suspend fun clientTalking(requests: Flow<HelloRequest>): HelloReply {
        val clientRequests = requests.toList()
        clientRequests.forEach { print(it) }
        return HelloReply.newBuilder()
            .setMessage("Received ${clientRequests.size} requests. Thx!")
            .build()
    }

    override fun talkingTogether(requests: Flow<HelloRequest>): Flow<HelloReply> = flow {
        requests.collect {
            val reply = HelloReply.newBuilder()
                .setMessage("Hello, ${it.name}.")
                .build()
            emit(reply)
        }
    }
}
