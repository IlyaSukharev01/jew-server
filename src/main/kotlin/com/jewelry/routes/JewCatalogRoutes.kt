package com.jewelry.routes

import com.jewelry.dao.jewelry_media.JewelryMediaManagement
import com.jewelry.dao.jewelry_items.JewelryFilters
import com.jewelry.dao.jewelry_items.JewelryManagement
import com.jewelry.dao.jewelry_items.JewelryTypes
import com.jewelry.dao.jewelry_saved.JewelrySavedManagement
import com.jewelry.dto.CatalogResponseData
import com.jewelry.dto.PreviewJewelryData
import com.jewelry.utils.ServerResponses.authorizationInfoIsMissing
import com.jewelry.utils.ServerResponses.jewListWasSent
import com.jewelry.utils.ServerResponses.suchJewTypeIsNotExists
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

private object RouteFilters {
    const val NEWEST    = "newest"
    const val UPWARD    = "upward"
    const val DOWNWARD  = "downward"
}

@ExperimentalUnsignedTypes
fun Route.configureCatalogRoutes() {
    val jewManagementDao by inject<JewelryManagement>()
    val jewMediaManagementDao by inject<JewelryMediaManagement>()
    val jewSavedManagementDao by inject <JewelrySavedManagement>()

    authenticate ("app") {
        route("/catalog") {
            get("/all") {
                val uuid = call.principal<JWTPrincipal>()?.payload?.getClaim("uuid")?.asString() ?: return@get call.respond(
                    HttpStatusCode.NonAuthoritativeInformation, CatalogResponseData(authorizationInfoIsMissing)
                )
                val filterBy = when (call.parameters["filterBy"]) {
                        RouteFilters.NEWEST     -> JewelryFilters.NEWEST
                        RouteFilters.DOWNWARD   -> JewelryFilters.EXPENSIVE_TO_CHEAP
                        RouteFilters.UPWARD     -> JewelryFilters.CHEAP_TO_EXPENSIVE
                        else                    -> JewelryFilters.MOST_VIEWED
                    }

                val limit = call.parameters["limit"]?.toInt()
                val offset = call.parameters["offset"]?.toLong()

                jewManagementDao.getAllJews(filterBy, limit ?: Int.MAX_VALUE, offset ?: 0).map { jewData ->
                    PreviewJewelryData(
                        jewData.jewId,
                        jewData.jewName,
                        jewData.price,
                        jewData.baseMaterial,
                        jewData.gemMaterial,
                        jewMediaManagementDao.getAllCropImagePaths(jewData.jewId),
                        jewSavedManagementDao.isExists(UUID.fromString(uuid), jewData.jewId)
                    )
                }.let { call.respond(HttpStatusCode.OK, CatalogResponseData(jewListWasSent, it)) }
            }
            get("/{type}") {
                val uuid = call.principal<JWTPrincipal>()?.payload?.getClaim("uuid")?.asString() ?: return@get call.respond(
                    HttpStatusCode.NonAuthoritativeInformation, CatalogResponseData(authorizationInfoIsMissing)
                )

                val jewType = when (call.parameters["type"]) {
                        JewelryTypes.RING.name.lowercase()      -> JewelryTypes.RING
                        JewelryTypes.EARRINGS.name.lowercase()  -> JewelryTypes.EARRINGS
                        JewelryTypes.BRACELET.name.lowercase()  -> JewelryTypes.BRACELET
                        else                                    -> return@get call.respond(HttpStatusCode.BadRequest, CatalogResponseData(suchJewTypeIsNotExists))
                    }

                val limit = call.parameters["limit"]?.toInt()
                val offset = call.parameters["offset"]?.toLong()

                jewManagementDao.getAllJewsByType(jewType, limit ?: Int.MAX_VALUE, offset ?: 0).map {jewData ->
                    PreviewJewelryData(
                        jewData.jewId,
                        jewData.jewName,
                        jewData.price,
                        jewData.baseMaterial,
                        jewData.gemMaterial,
                        jewMediaManagementDao.getAllCropImagePaths(jewData.jewId),
                        jewSavedManagementDao.isExists(UUID.fromString(uuid), jewData.jewId)
                    )
                }.let { call.respond (HttpStatusCode.OK, CatalogResponseData(jewListWasSent, it))}
            }
        }
    }
}
