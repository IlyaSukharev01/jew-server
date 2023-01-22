package com.jewelry.plugins

import com.jewelry.routes.configureUUIDAuth
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        configureUUIDAuth()
    }
}
