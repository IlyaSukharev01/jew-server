package com.jewelry.dao.jewelry_types

import org.jetbrains.exposed.dao.id.IntIdTable

object JewelryTypesTable : IntIdTable("jewelry_types") {
    val type = varchar ("jewelry_type", length = 255)
}