package io.spine.client

import io.grpc.stub.StreamObserver
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.spine.model.GreeterGrpc
import io.spine.model.HelloReply
import io.spine.model.HelloRequest
import javax.inject.Inject

@Controller("/client")
class ClientController(@Inject val client: GreeterGrpc.GreeterStub) {

    @Get(uri = "/sayHello", produces = ["text/plain"])
    fun sayHello(): String {
        println("Saying hello!")
        val observer = MemoizingObserver()
        val request = HelloRequest.newBuilder()
            .setName("World!")
            .build()
        client.withWaitForReady().sayHello(request, observer)
        observer.waitTillCompleted()
        val replies = observer.replies()
        println("All replies: $replies")
        return replies.toString()
    }

    @Get(uri = "/serverTalking", produces = ["text/plain"])
    fun serverTalking(): String {
        println("Server is talking!")
        val observer = MemoizingObserver()
        val request = HelloRequest.newBuilder()
            .setName("World!")
            .build()
        client.withWaitForReady().serverTalking(request, observer)
        observer.waitTillCompleted()
        val replies = observer.replies()
        println("All replies: $replies")
        return replies.toString()
    }

    @Get(uri = "/clientTalking", produces = ["text/plain"])
    fun clientTalking(): String {
        println("Client is talking!")
        val observer = MemoizingObserver()
        val requestConsumer = client.withWaitForReady().clientTalking(observer)
        for (i in 1..10) {
            val request = HelloRequest.newBuilder()
                .setName("World #${i}!")
                .build()
            requestConsumer.onNext(request)
            Thread.sleep(100)
        }
        requestConsumer.onCompleted()
        observer.waitTillCompleted()
        val replies = observer.replies()
        println("All replies: $replies")
        return replies.toString()
    }

    @Get(uri = "/talkingTogether", produces = ["text/plain"])
    fun talkingTogether(): String {
        println("Talking together!")
        val observer = MemoizingObserver()
        val requestConsumer = client.withWaitForReady().talkingTogether(observer)
        for (i in 1..5) {
            val request = HelloRequest.newBuilder()
                .setName("World #${i}!")
                .build()
            requestConsumer.onNext(request)
            Thread.sleep(100)
        }
        requestConsumer.onCompleted()
        observer.waitTillCompleted()
        val replies = observer.replies()
        println("All replies: $replies")
        return replies.toString()
    }
}

class MemoizingObserver : StreamObserver<HelloReply> {

    private val replies: MutableList<HelloReply?> = ArrayList()
    var completed = false

    fun replies(): List<HelloReply> {
        return replies.filterNotNull()
    }

    override fun onNext(value: HelloReply?) {
        println("New reply from the server: $value")
        replies.add(value)
    }

    override fun onError(t: Throwable?) {
        System.err.println("Error during processing.")
        t?.printStackTrace()
        completed = true
    }

    override fun onCompleted() {
        println("Completed.")
        completed = true
    }

    fun waitTillCompleted() {
        while (!completed) {
            println("Request not finished. Sleeping.")
            Thread.sleep(1000)
        }
    }
}
