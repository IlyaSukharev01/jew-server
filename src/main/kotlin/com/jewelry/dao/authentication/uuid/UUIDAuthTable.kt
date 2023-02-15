package com.jewelry.dao.authentication.uuid

import org.jetbrains.exposed.sql.Table

@ExperimentalUnsignedTypes
object UUIDAuthTable : Table("uuid_auth") {
    val uuid = uuid("uuid_token")

    override val primaryKey = PrimaryKey(uuid)
}