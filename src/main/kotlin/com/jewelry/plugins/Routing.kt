package com.jewelry.plugins

import com.jewelry.routes.configureAuthenticateRoutes
import com.jewelry.routes.catalog.configureCatalogRoutes
import com.jewelry.routes.items.configureJewItemRoutes
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.*
import java.io.File

fun Application.configureRouting() {
    install(Resources)

    routing {
        configureAuthenticateRoutes()
        configureCatalogRoutes()
        configureJewItemRoutes()

        static("/") {
            staticRootFolder = File("static")

            static("images") {
                files("crop")
                files("full")
            }
            static("videos") {
                files("mp4")
            }
        }

    }
}
