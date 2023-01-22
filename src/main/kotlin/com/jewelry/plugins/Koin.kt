package com.jewelry.plugins

import com.jewelry.dao.authentication.uuid.UUIDAuth
import com.jewelry.dao.authentication.uuid.UUIDAuthImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    val koinModule = module {
        single {environment}
        single<UUIDAuth> {UUIDAuthImpl()}
    }
    
    install(Koin) {
        slf4jLogger()
        modules(koinModule)
    }
}