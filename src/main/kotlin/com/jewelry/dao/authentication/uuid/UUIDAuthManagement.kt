package com.jewelry.dao.authentication.uuid

import com.jewelry.utils.DatabaseFactory.dbQuery
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.select
import java.util.UUID


interface UUIDAuthManagement {
    suspend fun addNew (deviceType: String, ip: String): UUID
    suspend fun isExists(uuid: UUID): Boolean
}
@ExperimentalUnsignedTypes
class UUIDAuthManagementIMPL : UUIDAuthManagement{
    override suspend fun addNew(deviceType: String, ip: String): UUID = dbQuery {
        UUIDAuthManager.new (UUID.randomUUID()){
            this.deviceType = deviceType
            this.ipAddress = ip
        }.id.value
    }

    override suspend fun isExists(uuid: UUID): Boolean = dbQuery {
        UUIDAuthTable.select {
            UUIDAuthTable.id eq uuid
        }.limit(1).singleOrNull() != null
    }
}

@ExperimentalUnsignedTypes
class UUIDAuthManager (uuid: EntityID<UUID>) : UUIDEntity(uuid) {
    companion object : UUIDEntityClass<UUIDAuthManager>(UUIDAuthTable)

    var deviceType by UUIDAuthTable.deviceType
    var ipAddress by UUIDAuthTable.ipAddress
}