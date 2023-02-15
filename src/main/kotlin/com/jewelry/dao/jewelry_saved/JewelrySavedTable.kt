package com.jewelry.dao.jewelry_saved

import org.jetbrains.exposed.sql.Table

@ExperimentalUnsignedTypes
object JewelrySavedTable : Table("jewelry_saved") {
    private val noteId = ulong("note_id").autoIncrement()
    val uuid = uuid("uuid")
    val jewId = ulong("jew_id")

    override val primaryKey: PrimaryKey = PrimaryKey(noteId);
}