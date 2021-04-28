package io.spine.client

import io.grpc.stub.StreamObserver
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.spine.model.GreeterGrpc
import io.spine.model.HelloReply
import io.spine.model.HelloRequest

@Controller("/client")
class ClientController {

    @Get(uri = "/sayHello", produces = ["text/plain"])
    fun sayHello(client: GreeterGrpc.GreeterStub): String {
        val observer = MemoizingObserver()
        val request = HelloRequest.newBuilder()
            .setName("World!")
            .build()
        client.sayHello(request, observer)
        val replies = observer.replies()
        println(replies)
        return replies.toString()
    }

    @Get(uri = "/serverTalking", produces = ["text/plain"])
    fun serverTalking(client: GreeterGrpc.GreeterStub): String {
        val observer = MemoizingObserver()
        val request = HelloRequest.newBuilder()
            .setName("World!")
            .build()
        client.serverTalking(request, observer)
        val replies = observer.replies()
        println(replies)
        return replies.toString()
    }

    @Get(uri = "/clientTalking", produces = ["text/plain"])
    fun clientTalking(client: GreeterGrpc.GreeterStub): String {
        val observer = MemoizingObserver()
        val requestConsumer = client.clientTalking(observer)
        for (i in 1..10) {
            val request = HelloRequest.newBuilder()
                .setName("World #${i}!")
                .build()
            requestConsumer.onNext(request)
            Thread.sleep(100)
        }
        requestConsumer.onCompleted()
        val replies = observer.replies()
        println(replies)
        return replies.toString()
    }

    @Get(uri = "/talkingTogether", produces = ["text/plain"])
    fun talkingTogether(client: GreeterGrpc.GreeterStub): String {
        val observer = MemoizingObserver()
        val requestConsumer = client.talkingTogether(observer)
        for (i in 1..5) {
            val request = HelloRequest.newBuilder()
                .setName("World #${i}!")
                .build()
            requestConsumer.onNext(request)
            Thread.sleep(100)
        }
        requestConsumer.onCompleted()
        val replies = observer.replies()
        println(replies)
        return replies.toString()
    }
}

class MemoizingObserver : StreamObserver<HelloReply> {

    private val replies: MutableList<HelloReply?> = ArrayList()

    fun replies(): List<HelloReply> {
        return replies.filterNotNull()
    }

    override fun onNext(value: HelloReply?) {
        replies.add(value)
    }

    override fun onError(t: Throwable?) {
        System.err.println("Error during processing.")
        t?.printStackTrace()
    }

    override fun onCompleted() {
        println("Completed.")
    }

}