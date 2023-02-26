package com.jewelry.dao.jewelry_items

import com.jewelry.dao.jewelry_types.JewelryTypesTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object JewelryItemsTable : LongIdTable("jewelry_items") {
    val jewelryName = varchar("jewelry_name", length = 255)
    val jewelryType = reference("jewelry_type", JewelryTypesTable, onDelete = ReferenceOption.NO_ACTION)
    val baseMaterial = varchar("base_material", length = 255)
    val addedDate = date("added_date").default(LocalDate.now())
    val price = integer("price")
    val weight = double("weight")
    val views = long("views")
    val gemMaterial = varchar("gem_material", length = 255).nullable()
    val gemsCount = integer("gems_count").nullable()
    val probe= integer ("probe").nullable()
}