package io.spine

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus

@Controller("/kotlinGrpc")
class KotlinGrpcController {

    @Get(uri="/", produces=["text/plain"])
    fun index(): String {
        return "Example Response"
    }
}