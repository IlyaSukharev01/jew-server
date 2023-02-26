package com.jewelry.plugins

import com.jewelry.exceptions.ExposedException
import com.jewelry.exceptions.TokenExceptions
import com.jewelry.utils.JwtCooker
import com.jewelry.utils.ServerResponses.tokenInvalidOrExpired
import com.jewelry.utils.ServerResponses.tokenInvalidOrExpiredCode
import com.jewelry.utils.ServerResponses.tokenUUIDInfoMissing
import com.jewelry.utils.ServerResponses.tokenUUIDInfoMissingCode
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val jwtCookerDao by inject<JwtCooker>()

    authentication {
        jwt("app") {
            verifier(jwtCookerDao.verifyJwt())

            validate { credential ->
                if (credential.payload.getClaim("uuid").asString() != null)   JWTPrincipal(credential.payload)
                else                                                                throw TokenExceptions(tokenUUIDInfoMissingCode, tokenUUIDInfoMissing)
            }
            challenge { _, _ ->
                throw ExposedException(tokenInvalidOrExpiredCode, tokenInvalidOrExpired)
            }
        }
    }
}
