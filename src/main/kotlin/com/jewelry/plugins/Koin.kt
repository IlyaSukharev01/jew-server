package com.jewelry.plugins

import com.jewelry.dao.authentication.uuid.UUIDAuthManagement
import com.jewelry.dao.authentication.uuid.UUIDAuthManagementIMPL
import com.jewelry.dao.jewelry_media.JewelryMediaManagement
import com.jewelry.dao.jewelry_media.JewelryMediaManagementIMPL
import com.jewelry.dao.jewelry_items.JewelryManagement
import com.jewelry.dao.jewelry_items.JewelryManagementIMPL
import com.jewelry.dao.jewelry_saved.JewelrySavedManagement
import com.jewelry.dao.jewelry_saved.JewelrySavedManagementIMPL
import com.jewelry.utils.JwtCooker
import com.jewelry.utils.JwtCookerIMPL
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

@ExperimentalUnsignedTypes
fun Application.configureKoin() {
    val koinModule = module {
        single<UUIDAuthManagement> { UUIDAuthManagementIMPL() }
        single<JewelryManagement> { JewelryManagementIMPL() }
        single<JewelryMediaManagement> { JewelryMediaManagementIMPL() }
        single<JewelrySavedManagement> { JewelrySavedManagementIMPL() }
        single<JwtCooker> { JwtCookerIMPL() }
    }

    install(Koin) {
        slf4jLogger()
        modules(koinModule)
    }
}