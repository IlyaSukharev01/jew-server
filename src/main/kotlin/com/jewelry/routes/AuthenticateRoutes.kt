package com.jewelry.routes

import com.jewelry.dao.authentication.uuid.UUIDAuthManagement
import com.jewelry.dto.AuthenticationData
import com.jewelry.dto.AuthenticationResponseData
import com.jewelry.utils.JwtCooker
import com.jewelry.utils.ServerResponses.internalServerError
import com.jewelry.utils.ServerResponses.notEnoughAuthInfo
import com.jewelry.utils.ServerResponses.tokenWasSent
import com.jewelry.utils.ServerResponses.uuidIsNotExists
import com.jewelry.utils.ServerResponses.uuidIsNull
import com.jewelry.utils.ServerResponses.uuidWasSent
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.configureAuthenticateRoutes() {
    val uuidAuthDao by inject<UUIDAuthManagement>()
    val jwtCookerDao by inject<JwtCooker>()

    route("auth/uuid") {
        get {
            var uuid = UUID.randomUUID()

            while(uuidAuthDao.isUUIDExists(uuid)) {
                uuid = UUID.randomUUID()
            }

            if (!uuidAuthDao.saveUUID(uuid)) {
                call.respond(HttpStatusCode.InternalServerError, AuthenticationResponseData(internalServerError))
            }

            call.respond(HttpStatusCode.OK, AuthenticationResponseData(uuidWasSent, uuid = uuid.toString()))
        }
    }
    route("auth/token") {
        post {
            val data = call.receive<AuthenticationData>()

            if (!uuidAuthDao.isUUIDExists(UUID.fromString(data.uuid))) {
                call.respond(HttpStatusCode.BadRequest, AuthenticationResponseData(uuidIsNotExists))
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    AuthenticationResponseData(
                        tokenWasSent,
                        accessToken = jwtCookerDao.buildAccessJwt(data.uuid),
                        refreshToken = jwtCookerDao.buildRefreshJwt(data.uuid)
                    )
                )
            }
        }
        authenticate ("app") {
            get("/update") {
                call.principal<JWTPrincipal>()?.payload?.let {
                    val uuid = it.getClaim("uuid").asString() ?:
                        return@get call.respond(HttpStatusCode.NonAuthoritativeInformation, AuthenticationResponseData(uuidIsNull))

                    it.getClaim("type").asString().let {type ->
                        if (type == null || type != "refresh") {
                            return@get call.respond(HttpStatusCode.Unauthorized, AuthenticationResponseData(notEnoughAuthInfo))
                        }
                    }

                    call.respond (
                        HttpStatusCode.OK,
                        AuthenticationResponseData(tokenWasSent, accessToken = jwtCookerDao.buildAccessJwt(uuid))
                    )
                }
            }
        }
    }
}