package io.spine.client

import io.grpc.stub.StreamObserver
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.spine.GreeterGrpc
import io.spine.HelloReply
import io.spine.HelloRequest

@Controller("/kotlinGrpc")
class KotlinGrpcController {

    @Get(uri = "/", produces = ["text/plain"])
    fun index(client: GreeterGrpc.GreeterStub): List<HelloReply> {
        val observer = MemoizingObserver()
        val request = HelloRequest.newBuilder()
            .setName("World!")
            .build()
        client.sayHello(request, observer)
        val replies = observer.replies()
        println(replies)
        return replies
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