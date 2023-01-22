package com.jewelry.routes

import com.jewelry.dao.authentication.uuid.UUIDAuth
import com.jewelry.dto.AuthenticationData
import com.jewelry.dto.AuthenticationResponseData
import com.jewelry.utils.ServerResponses.internalServerError
import com.jewelry.utils.ServerResponses.uuidIsCorrect
import com.jewelry.utils.ServerResponses.uuidIsNotCorrect
import com.jewelry.utils.ServerResponses.uuidWasSent
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.configureUUIDAuth() {
    val uuidAuthDao by inject<UUIDAuth>()

    route("/uuid") {
        get("/get") {
            var uuid = UUID.randomUUID()

            while(uuidAuthDao.isUUIDExists(uuid))
                uuid = UUID.randomUUID()

            if (!uuidAuthDao.saveUUID(uuid))
                call.respond(HttpStatusCode.InternalServerError, internalServerError)

            call.respond(HttpStatusCode.OK, AuthenticationResponseData(uuidWasSent, uuid.toString()))
        }
        post("/verify") {
            call.receive<AuthenticationData>().let { data ->
                if (!uuidAuthDao.isUUIDExists(UUID.fromString(data.uuidToken)))     call.respond(HttpStatusCode.Unauthorized, uuidIsNotCorrect)
                else                                                                call.respond(HttpStatusCode.Accepted, uuidIsCorrect)
            }
        }
    }
}