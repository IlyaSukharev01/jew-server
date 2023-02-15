package com.jewelry.plugins

import com.jewelry.utils.JwtCooker
import com.jewelry.utils.ServerResponses.tokenIsNotValidOrExpired
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val jwtCookerDao by inject<JwtCooker>()

    authentication {
        jwt ("app") {
            verifier(jwtCookerDao.verifyJwt())

            validate {credential ->
                if (jwtCookerDao.isCredentialUseful(credential)){
                    JWTPrincipal(credential.payload)
                }
                else return@validate null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, tokenIsNotValidOrExpired)
            }
        }
    }
}
