package com.pcbaecker

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var ww = WorldwideData()

fun main() {
    GlobalScope.launch {
        while(true) {
            val dwd = WorldwideClientImpl()
            ww = dwd.download()
            delay(3600000)
        }
    }

    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson {
            }
        }
        routing {
            get("/worldwide") {
                call.respond(ww)
            }
        }
    }
    server.start(wait = true)
}