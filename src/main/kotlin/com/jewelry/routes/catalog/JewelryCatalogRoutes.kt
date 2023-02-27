package com.jewelry.routes.catalog

import com.jewelry.dao.jewelry_items.JewelryManagement
import com.jewelry.dao.jewelry_media.JewelryMediaManagement
import com.jewelry.dao.jewelry_saved.JewelrySavedManagement
import com.jewelry.dto.CatalogResponseData
import com.jewelry.dto.PreviewJewelryData
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.configureCatalogRoutes() {
    val jewelryManagementDao by inject<JewelryManagement>()
    val jewelryMediaManagementDao by inject<JewelryMediaManagement>()
    val jewelrySavedManagementDao by inject<JewelrySavedManagement>()

    authenticate("app") {
        documentation()

        get<Catalog> { catalog ->
            val uuid = call.principal<JWTPrincipal>()?.payload?.getClaim("uuid")!!.asString()

            jewelryManagementDao.getAllJewelries(catalog.orderBy, catalog.limit, catalog.offset).map { data ->
                data.jewelryId!!.let {
                    PreviewJewelryData(
                        it,
                        data.jewelryName,
                        data.price,
                        data.baseMaterial,
                        data.gemMaterial,
                        jewelryMediaManagementDao.getAllCropImagePaths(it),
                        jewelrySavedManagementDao.isExists(UUID.fromString(uuid), it)
                    )
                }
            }.let { call.respond(HttpStatusCode.OK, CatalogResponseData(it)) }
        }
        get<Catalog.Type> { catalog ->
            val uuid = call.principal<JWTPrincipal>()?.payload?.getClaim("uuid")!!.asString()

            jewelryManagementDao.getAllJewelriesByType(
                catalog.type,
                catalog.parent.orderBy,
                catalog.parent.limit,
                catalog.parent.offset
            ).map { data ->
                data.jewelryId!!.let {
                    PreviewJewelryData(
                        it,
                        data.jewelryName,
                        data.price,
                        data.baseMaterial,
                        data.gemMaterial,
                        jewelryMediaManagementDao.getAllCropImagePaths(it),
                        jewelrySavedManagementDao.isExists(UUID.fromString(uuid), it)
                    )
                }
            }.let { call.respond(HttpStatusCode.OK, CatalogResponseData(it)) }
        }
    }
}
