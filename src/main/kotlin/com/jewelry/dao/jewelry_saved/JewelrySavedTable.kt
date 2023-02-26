package com.jewelry.dao.jewelry_saved

import org.jetbrains.exposed.dao.id.LongIdTable

object JewelrySavedTable : LongIdTable("jewelry_saved") {
    val uuid = uuid("uuid")
    val jewelryId = long("jewelry_id")
}