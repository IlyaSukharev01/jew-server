package com.jewelry

import com.jewelry.plugins.*
import io.ktor.server.application.*
import com.jewelry.utils.DatabaseFactory.configurePostgresDatabase

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureSerialization()
    configureKoin()
    configurePostgresDatabase()
    configureValidator()
    configureStatusPages()
}
