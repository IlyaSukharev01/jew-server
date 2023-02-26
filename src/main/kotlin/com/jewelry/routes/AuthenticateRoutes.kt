package com.jewelry.routes

import com.jewelry.dao.authentication.uuid.UUIDAuthManagement
import com.jewelry.dto.AuthenticationResponseData
import com.jewelry.dto.DeviceTypeData
import com.jewelry.dto.UUIDData
import com.jewelry.exceptions.ExposedException
import com.jewelry.exceptions.TokenExceptions
import com.jewelry.utils.JwtCooker
import com.jewelry.utils.ServerResponses
import com.jewelry.utils.ServerResponses.tokenTypeInfoMissing
import com.jewelry.utils.ServerResponses.tokenTypeInfoMissingCode
import com.jewelry.utils.ServerResponses.tokenUUIDInfoMissing
import com.jewelry.utils.ServerResponses.tokenUUIDInfoMissingCode
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.configureAuthenticateRoutes() {
    val jwtCookerDao by inject<JwtCooker>()
    val uuidAuthDao by inject<UUIDAuthManagement>()

    route("auth/uuid") {
        post {
            call.receive<DeviceTypeData>().deviceType.let { deviceType ->
                call.respond(uuidAuthDao.addNew(deviceType, call.request.origin.remoteHost).toString())
            }
        }
    }
    route("auth/token") {
        post {
            val data = call.receive<UUIDData>()

            if (!uuidAuthDao.isExists(UUID.fromString(data.uuid)))
                throw ExposedException(ServerResponses.uuidNotFound, ServerResponses.uuidNotFoundCode)

            call.respond(
                HttpStatusCode.OK,
                AuthenticationResponseData(
                    accessToken = jwtCookerDao.buildAccessJwt(data.uuid),
                    refreshToken = jwtCookerDao.buildRefreshJwt(data.uuid)
                )
            )
        }
        authenticate("app") {
            get("/update") {
                call.principal<JWTPrincipal>()?.payload?.let {payload ->
                    val uuid = payload.getClaim("uuid").asString()!!

                    payload.getClaim("type").asString().let {type ->
                        if (type != "refresh")  throw TokenExceptions(tokenTypeInfoMissingCode, tokenTypeInfoMissing)
                    }

                    call.respond (
                        HttpStatusCode.OK,
                        AuthenticationResponseData(accessToken = jwtCookerDao.buildAccessJwt(uuid))
                    )
                }
            }
        }
    }
}