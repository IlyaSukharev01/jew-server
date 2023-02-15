package com.jewelry.dao.jewelry_items

import com.jewelry.dao.jewelry_media.JewelryMediaTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

@ExperimentalUnsignedTypes
object JewelryItemsTable : Table("jewelry_items") {
    val jewId = ulong("jew_id").autoIncrement()
    val jewName = varchar("jew_name", length = 255)
    val jewType = varchar("jew_type", length = 255)
    val baseMaterial = varchar("base_material", length = 255)
    val addedDate = date("added_date")
    val price = uinteger("price")
    val weight = double("weight")
    val views = ulong("views")
    val gemMaterial = varchar("gem_material", length = 255).nullable()
    val gemsCount = uinteger("gems_count").nullable()
    val probe= uinteger ("probe").nullable()

    override val primaryKey = PrimaryKey(JewelryMediaTable.jewId)
}