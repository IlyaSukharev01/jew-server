package com.jewelry.utils

import com.jewelry.dao.authentication.uuid.UUIDAuthTable
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DatabaseFactory: KoinComponent {
    private val environment by inject<ApplicationEnvironment>()

    fun configurePostgresDatabase() {
        val url = environment.config.property("postgres.url").getString()
        val driver = environment.config.property("postgres.driver").getString()
        val user = environment.config.property("postgres.user").getString()
        val pwd = environment.config.property("postgres.password").getString()

        Database.connect(url, driver, user, pwd).let {
            transaction(it) {
                SchemaUtils.create(UUIDAuthTable)
            }
        }
    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}