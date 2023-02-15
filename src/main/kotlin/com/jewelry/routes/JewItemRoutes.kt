package com.jewelry.routes

import com.jewelry.dao.jewelry_items.JewelryManagement
import com.jewelry.dao.jewelry_media.JewelryMediaManagement
import com.jewelry.dao.jewelry_saved.JewelrySavedManagement
import com.jewelry.dto.DetailedJewelryData
import com.jewelry.dto.JewelryItemResponseData
import com.jewelry.utils.ServerResponses.authorizationInfoIsMissing
import com.jewelry.utils.ServerResponses.jewItemWasNotFound
import com.jewelry.utils.ServerResponses.jewItemWasNotSaved
import com.jewelry.utils.ServerResponses.jewItemWasSaved
import com.jewelry.utils.ServerResponses.jewItemWasSent
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.configureJewItemRoutes() {
    val jewManagementDao by inject<JewelryManagement>()
    val jewMediaManagementDao by inject<JewelryMediaManagement>()
    val jewSavedManagementDao by inject <JewelrySavedManagement>()


    authenticate ("app") {
        route("/item/{id}") {
            get {
                val uuid = call.principal<JWTPrincipal>()?.payload?.getClaim("uuid")?.asString() ?: return@get call.respond(
                    HttpStatusCode.NonAuthoritativeInformation, JewelryItemResponseData(authorizationInfoIsMissing)
                )
                val jewId = call.parameters["id"]!!.toULong()

                jewManagementDao.getJew(jewId)?.let {
                    jewManagementDao.increaseJewViews(jewId)
                    DetailedJewelryData (
                        jewId,
                        it.jewName,
                        it.price,
                        it.baseMaterial,
                        it.weight,
                        it.gemMaterial,
                        it.gemsCount,
                        it.probe,
                        jewSavedManagementDao.isExists(UUID.fromString(uuid), jewId),
                        jewMediaManagementDao.getAllFullImagePaths(jewId),
                        jewMediaManagementDao.getAllVideoPaths(jewId)
                    ).let { data -> call.respond(HttpStatusCode.OK, JewelryItemResponseData(jewItemWasSent, data)) }
                } ?: call.respond(HttpStatusCode.InternalServerError, JewelryItemResponseData(jewItemWasNotFound))
            }
            get("/save") {
                val uuid = call.principal<JWTPrincipal>()?.payload?.getClaim("uuid")?.asString() ?: return@get call.respond(
                    HttpStatusCode.NonAuthoritativeInformation, JewelryItemResponseData(authorizationInfoIsMissing)
                )

                call.parameters["id"]!!.toULong().let {jewId ->
                    if (!jewSavedManagementDao.addSavedJew(UUID.fromString(uuid), jewId)) {
                        call.respond(HttpStatusCode.InternalServerError, JewelryItemResponseData(jewItemWasNotSaved))
                    } else {
                        call.respond(HttpStatusCode.OK, JewelryItemResponseData(jewItemWasSaved))
                    }
                }
            }
        }
    }
}