package io.spine.server

import io.micronaut.runtime.Micronaut.build

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        build()
            .args(*args)
            .packages("io.spine.server")
            .start()
    }
}
