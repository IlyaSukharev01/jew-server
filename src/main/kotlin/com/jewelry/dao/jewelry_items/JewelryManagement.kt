package com.jewelry.dao.jewelry_items

import com.jewelry.dto.JewelryData
import com.jewelry.utils.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus

enum class JewelryFilters {
    MOST_VIEWED, NEWEST, EXPENSIVE_TO_CHEAP, CHEAP_TO_EXPENSIVE,
}

enum class JewelryTypes {
    EARRINGS, BRACELET, RING
}

interface JewelryManagement {
    suspend fun saveJew(jew: JewelryData): Boolean
    suspend fun getAllJews(filter: JewelryFilters, limit: Int, offset: Long): List<JewelryData>
    suspend fun getAllJewsByType (type: JewelryTypes, limit: Int, offset: Long): List<JewelryData>
    suspend fun getJew(jewId: ULong): JewelryData?
    suspend fun increaseJewViews(jewId: ULong): Boolean
}

@ExperimentalUnsignedTypes
class JewelryManagementIMPL : JewelryManagement {
    override suspend fun saveJew(jew: JewelryData): Boolean = dbQuery {
        JewelryItemsTable.insert {
            it[jewName] = jew.jewName
            it[jewType] = jew.jewType
            it[price] = jew.price
            it[baseMaterial] = jew.baseMaterial
            it[addedDate] = jew.addedDate!!
            it[weight] = jew.weight!!
            it[gemMaterial] = jew.gemMaterial
            it[gemsCount] = jew.gemsCount
            it[probe] = jew.probe
            it[views] = jew.views
        }.resultedValues != null
    }

    private fun rowToData(row: ResultRow): JewelryData = JewelryData (
        jewId = row[JewelryItemsTable.jewId],
        jewName = row[JewelryItemsTable.jewName],
        jewType = row[JewelryItemsTable.jewType],
        price = row[JewelryItemsTable.price],
        baseMaterial = row[JewelryItemsTable.baseMaterial],
        gemMaterial = row[JewelryItemsTable.gemMaterial],
        weight = row[JewelryItemsTable.weight],
        gemsCount = row[JewelryItemsTable.gemsCount],
        probe = row[JewelryItemsTable.probe],
    )

    override suspend fun getAllJews(filter: JewelryFilters, limit: Int, offset: Long): List<JewelryData> = dbQuery {
        when (filter) {
            JewelryFilters.MOST_VIEWED        -> JewelryItemsTable.selectAll().limit(limit, offset).orderBy(JewelryItemsTable.views to SortOrder.DESC)
            JewelryFilters.NEWEST             -> JewelryItemsTable.selectAll().limit(limit, offset).orderBy(JewelryItemsTable.addedDate to SortOrder.DESC)
            JewelryFilters.CHEAP_TO_EXPENSIVE -> JewelryItemsTable.selectAll().limit(limit, offset).orderBy(JewelryItemsTable.price to SortOrder.ASC)
            JewelryFilters.EXPENSIVE_TO_CHEAP -> JewelryItemsTable.selectAll().limit(limit, offset).orderBy(JewelryItemsTable.price to SortOrder.DESC)
        }.map { rowToData(it) }
    }

    override suspend fun getAllJewsByType(type: JewelryTypes, limit: Int, offset: Long): List<JewelryData> = dbQuery {
        JewelryItemsTable.select {
            JewelryItemsTable.jewType eq type.name.lowercase()
        }.limit(limit, offset).map { rowToData(it) }
    }

    override suspend fun getJew(jewId: ULong): JewelryData? = dbQuery {
        JewelryItemsTable.select {
            JewelryItemsTable.jewId eq jewId
        }.limit(1).singleOrNull()?.let {rowToData(it)}
    }

    override suspend fun increaseJewViews(jewId: ULong): Boolean = dbQuery {
        JewelryItemsTable.update ({ JewelryItemsTable.jewId eq jewId}) {
            it[views] = views plus 1U
        } > 0
    }
}