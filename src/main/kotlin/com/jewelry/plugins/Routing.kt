package com.jewelry.plugins

import com.jewelry.routes.configureAuthenticateRoutes
import com.jewelry.routes.configureCatalogRoutes
import com.jewelry.routes.configureJewItemRoutes
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*

@ExperimentalUnsignedTypes
fun Application.configureRouting() {
    routing {
        configureAuthenticateRoutes()
        configureCatalogRoutes()
        configureJewItemRoutes()


        static("/") {
            staticBasePackage = "static"

            static("images") {
                resources("crop")
                resources("full")
            }
            static("videos") {
                resources("mp4")
            }
        }

    }
}
