package com.jewelry.utils

import com.jewelry.dao.authentication.uuid.UUIDAuthTable
import com.jewelry.dao.jewelry_media.JewelryMediaTable
import com.jewelry.dao.jewelry_items.JewelryItemsTable
import com.jewelry.dao.jewelry_saved.JewelrySavedTable
import com.jewelry.dao.jewelry_types.JewelryTypesTable
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun configurePostgresDatabase() {
        val config = HoconApplicationConfig(ConfigFactory.load())

        val url = config.property("postgres.url").getString()
        val driver = config.property("postgres.driver").getString()
        val user = config.property("postgres.user").getString()
        val pwd = config.property("postgres.password").getString()

        transaction(Database.connect(url, driver, user, pwd)) {
            SchemaUtils.create(UUIDAuthTable)
            SchemaUtils.create(JewelryMediaTable)
            SchemaUtils.create(JewelryItemsTable)
            SchemaUtils.create(JewelryTypesTable)
            SchemaUtils.create(JewelrySavedTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}