package com.jewelry

import com.jewelry.plugins.*
import com.jewelry.utils.DatabaseFactory.configurePostgresDatabase
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureKoin()
    configureSecurity()
    configureRouting()
    configureSerialization()
    configureValidator()
    configureStatusPages()
    configurePostgresDatabase()
}
