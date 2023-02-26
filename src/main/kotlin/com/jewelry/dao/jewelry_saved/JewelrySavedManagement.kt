package com.jewelry.dao.jewelry_saved

import com.jewelry.utils.DatabaseFactory.dbQuery
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import java.util.*

interface JewelrySavedManagement {
    suspend fun isExists(uuid: UUID, jewelryId: Long): Boolean
    suspend fun addJewelry (uuid: UUID, jewelryId: Long)
}

class JewelrySavedManagementIMPL : JewelrySavedManagement {
    override suspend fun isExists(uuid: UUID, jewelryId: Long): Boolean = dbQuery {
        JewelrySavedTable.select {
            (JewelrySavedTable.uuid eq uuid) and (JewelrySavedTable.jewelryId eq jewelryId)
        }.limit(1).singleOrNull() != null
    }

    override suspend fun addJewelry (uuid: UUID, jewelryId: Long): Unit = dbQuery {
        JewelrySavedManager.new {
            this.uuid = uuid
            this.jewelryId = jewelryId
        }
    }
}
class JewelrySavedManager (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<JewelrySavedManager>(JewelrySavedTable)
    var uuid by JewelrySavedTable.uuid
    var jewelryId by JewelrySavedTable.jewelryId
}