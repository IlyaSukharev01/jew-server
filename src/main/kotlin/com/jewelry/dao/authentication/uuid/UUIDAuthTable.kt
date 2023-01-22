package com.jewelry.dao.authentication.uuid

import org.jetbrains.exposed.sql.Table

object UUIDAuthTable : Table("uuid_auth") {
    private val noteId = ulong("note_id").autoIncrement()
    val uuid = uuid("uuid_token")

    override val primaryKey = PrimaryKey(noteId)
}