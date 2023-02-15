package com.jewelry.dao.authentication.uuid

import com.jewelry.utils.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.UUID

interface UUIDAuthManagement {
    suspend fun saveUUID(uuid: UUID): Boolean
    suspend fun isUUIDExists(uuid: UUID): Boolean
}

@ExperimentalUnsignedTypes
class UUIDAuthManagementIMPL : UUIDAuthManagement {
    override suspend fun saveUUID(uuid: UUID): Boolean = dbQuery {
        UUIDAuthTable.insert {
            it[UUIDAuthTable.uuid] = uuid
        }.resultedValues != null
    }

    override suspend fun isUUIDExists(uuid: UUID): Boolean = dbQuery {
        UUIDAuthTable.select {
            UUIDAuthTable.uuid eq uuid
        }.limit(1).singleOrNull() != null
    }

}