package com.jewelry.dao.jewelry_items

import com.jewelry.dao.jewelry_types.JewelryTypesManager
import com.jewelry.dao.jewelry_types.JewelryTypesTable
import com.jewelry.dto.JewelryData
import com.jewelry.exceptions.ExposedException
import com.jewelry.utils.DatabaseFactory.dbQuery
import com.jewelry.utils.ServerResponses.itemNotFound
import com.jewelry.utils.ServerResponses.itemNotFoundCode
import com.jewelry.utils.ServerResponses.suchTypeIsNotExists
import com.jewelry.utils.ServerResponses.suchTypeIsNotExistsCode
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*

object JewelryOrders {
    const val NEW       = "new"
    const val UPWARD    = "upward"
    const val DOWNWARD  = "downward"
    const val POPULAR   = "popular"
}

interface JewelryManagement {
    suspend fun addJewelry(data: JewelryData, jewelryTypesManager: JewelryTypesManager)
    suspend fun getAllJewelries(order: String?, limit: Int, offset: Long): List<JewelryData>
    suspend fun getAllJewelriesByType (type: String, order: String?, limit: Int, offset: Long): List<JewelryData>
    suspend fun getJewelry(jewelryId: Long): JewelryItemsManager
}

class JewelryManagementIMPL : JewelryManagement {
    override suspend fun addJewelry(data: JewelryData, jewelryTypesManager: JewelryTypesManager): Unit = dbQuery {
        JewelryItemsManager.new {
            this.jewelryName = data.jewelryName
            this.jewelryType = jewelryTypesManager
            this.price = data.price
            this.baseMaterial = data.baseMaterial
            this.weight = data.weight!!
            this.gemMaterial = gemMaterial!!
            this.gemsCount = data.gemsCount!!
            this.probe = data.probe!!
            this.views = data.views
        }
    }

    private fun rowToData(row: ResultRow): JewelryData = JewelryData (
        jewelryId = row[JewelryItemsTable.id].value,
        jewelryName = row[JewelryItemsTable.jewelryName],
        jewelryType = row[JewelryItemsTable.jewelryType].value,
        price = row[JewelryItemsTable.price],
        baseMaterial = row[JewelryItemsTable.baseMaterial],
        gemMaterial = row[JewelryItemsTable.gemMaterial],
        weight = row[JewelryItemsTable.weight],
        gemsCount = row[JewelryItemsTable.gemsCount],
        probe = row[JewelryItemsTable.probe],
    )


    private suspend fun getOrderedItems (order: String?, query: Query): List<JewelryData> = dbQuery {
        when (order) {
            JewelryOrders.POPULAR   -> JewelryItemsTable.views to SortOrder.DESC
            JewelryOrders.NEW       -> JewelryItemsTable.addedDate to SortOrder.DESC
            JewelryOrders.UPWARD    -> JewelryItemsTable.price to SortOrder.ASC
            JewelryOrders.DOWNWARD  -> JewelryItemsTable.price to SortOrder.DESC
            else                    -> JewelryItemsTable.id to SortOrder.ASC
        }
            .let { query.orderBy(it) }
            .map { rowToData(it) }
    }

    override suspend fun getAllJewelries(order: String?, limit: Int, offset: Long): List<JewelryData> = dbQuery {
        getOrderedItems (order, JewelryItemsTable.selectAll().limit(limit, offset))
    }

    override suspend fun getAllJewelriesByType(type: String, order: String?, limit: Int, offset: Long): List<JewelryData> = dbQuery {
        val typeId = JewelryTypesTable.select { JewelryTypesTable.type eq type }.limit(1).singleOrNull()?.let {
            it[JewelryTypesTable.id].value } ?: throw ExposedException(suchTypeIsNotExistsCode, suchTypeIsNotExists, type)

        getOrderedItems (order, JewelryItemsTable.select {JewelryItemsTable.jewelryType eq typeId}.limit(limit, offset))
    }

    override suspend fun getJewelry(jewelryId: Long): JewelryItemsManager = dbQuery {
        JewelryItemsManager.findById(jewelryId) ?: throw ExposedException(itemNotFoundCode, itemNotFound)
    }
}

class JewelryItemsManager (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<JewelryItemsManager>(JewelryItemsTable)

    var jewelryName by JewelryItemsTable.jewelryName
    var jewelryType by JewelryTypesManager referencedOn JewelryItemsTable.jewelryType
    var baseMaterial by JewelryItemsTable.baseMaterial
    var price by JewelryItemsTable.price
    var weight by JewelryItemsTable.weight
    var views by JewelryItemsTable.views
    var gemMaterial by JewelryItemsTable.gemMaterial
    var gemsCount by JewelryItemsTable.gemsCount
    var probe by JewelryItemsTable.probe
}