package com.jewelry.plugins

import com.jewelry.exceptions.ExposedException
import com.jewelry.exceptions.RequestInformationExceptions
import com.jewelry.exceptions.TokenExceptions
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.single())
        }
        exception<NumberFormatException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, "${cause.message}")
        }
        exception<IllegalArgumentException> {call, cause ->
            call.respond(HttpStatusCode.BadRequest, "${cause.message}")
        }
        exception<ExposedException> {call, cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.getFullDescription())
        }
        exception<TokenExceptions> {call, cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.getFullDescription())
        }
        exception<RequestInformationExceptions> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.getFullDescription())
        }
    }
}