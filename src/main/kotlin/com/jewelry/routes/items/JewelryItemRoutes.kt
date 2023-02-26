package com.jewelry.routes.items

import com.jewelry.dao.jewelry_items.JewelryManagement
import com.jewelry.dao.jewelry_media.JewelryMediaManagement
import com.jewelry.dao.jewelry_saved.JewelrySavedManagement
import com.jewelry.dto.DetailedJewelryData
import com.jewelry.dto.JewelryItemResponseData
import com.jewelry.utils.DatabaseFactory.dbQuery
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.configureJewItemRoutes() {
    val jewelryManagementDao by inject<JewelryManagement>()
    val jewelryMediaManagementDao by inject<JewelryMediaManagement>()
    val jewelrySavedManagementDao by inject <JewelrySavedManagement>()


    authenticate ("app") {
        get<JewelryItem.Id> {item ->
            val uuid = call.principal<JWTPrincipal>()?.payload?.getClaim("uuid")!!.asString()

            jewelryManagementDao.getJewelry(item.id).let {data ->
                DetailedJewelryData (
                    item.id,
                    data.jewelryName,
                    data.price,
                    data.baseMaterial,
                    data.weight,
                    data.gemMaterial,
                    data.gemsCount,
                    data.probe,
                    jewelrySavedManagementDao.isExists(UUID.fromString(uuid), item.id),
                    jewelryMediaManagementDao.getAllFullImagePaths(item.id),
                    jewelryMediaManagementDao.getAllVideoPaths(item.id)
                ).let {
                    call.respond(HttpStatusCode.OK, JewelryItemResponseData(it))
                    dbQuery { data.views++ }
                }
            }
        }
        get<JewelryItem.Id.Save> { item ->
            val uuid = call.principal<JWTPrincipal>()?.payload?.getClaim("uuid")!!.asString()

            jewelrySavedManagementDao.addJewelry(UUID.fromString(uuid), item.parent.id)
            call.respond(HttpStatusCode.Created)

        }
    }
}