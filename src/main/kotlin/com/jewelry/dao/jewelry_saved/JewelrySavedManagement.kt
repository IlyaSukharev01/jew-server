package com.jewelry.dao.jewelry_saved

import com.jewelry.utils.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

interface JewelrySavedManagement {
    suspend fun isExists(uuid: UUID, jewId: ULong): Boolean
    suspend fun addSavedJew(uuid: UUID, jewId: ULong): Boolean
}

@ExperimentalUnsignedTypes
class JewelrySavedManagementIMPL : JewelrySavedManagement {
    override suspend fun isExists(uuid: UUID, jewId: ULong): Boolean = dbQuery {
        JewelrySavedTable.select {
            (JewelrySavedTable.uuid eq uuid) and (JewelrySavedTable.jewId eq jewId)
        }.limit(1).singleOrNull() != null
    }

    override suspend fun addSavedJew(uuid: UUID, jewId: ULong): Boolean = dbQuery {
        JewelrySavedTable.insert {
            it[JewelrySavedTable.uuid] = uuid
            it[JewelrySavedTable.jewId] = jewId
        }.resultedValues != null
    }
}