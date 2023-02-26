package com.jewelry.dao.jewelry_types

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class JewelryTypesManager (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<JewelryTypesManager>(JewelryTypesTable)
    var type by JewelryTypesTable.type
}