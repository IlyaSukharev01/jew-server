package com.jewelry.plugins

import com.jewelry.routes.configureAuthenticateRoutes
import com.jewelry.routes.catalog.configureCatalogRoutes
import com.jewelry.routes.items.configureJewItemRoutes
import com.jewelry.routes.test.testRoutes
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.*

fun Application.configureRouting() {
    install(Resources)

    routing {
        testRoutes()
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
