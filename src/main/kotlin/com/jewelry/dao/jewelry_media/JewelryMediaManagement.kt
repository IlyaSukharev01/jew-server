package com.jewelry.dao.jewelry_media

import com.jewelry.dto.JewelryMediaData
import com.jewelry.utils.DatabaseFactory.dbQuery
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*

interface JewelryMediaManagement {
    suspend fun getAllCropImagePaths(jewelryId: Long): List<String>
    suspend fun getAllFullImagePaths(jewelryId: Long): List<String>
    suspend fun getAllVideoPaths(jewelryId: Long): List<String>
    suspend fun saveMediaPaths(jewelryId: Long, data: JewelryMediaData)
}

class JewelryMediaManagementIMPL : JewelryMediaManagement {
    private fun getList(jewelryId: Long, expression: Column<String?>): List<String> {
        return JewelryMediaTable
            .slice(expression)
            .select {JewelryMediaTable.jewelryId eq jewelryId }
            .mapNotNull { it[expression] }
    }

    override suspend fun getAllCropImagePaths(jewelryId: Long): List<String> = dbQuery {
        getList(jewelryId, JewelryMediaTable.cropImgPath)
    }

    override suspend fun getAllFullImagePaths(jewelryId: Long): List<String> = dbQuery {
        getList(jewelryId, JewelryMediaTable.fullImgPath)
    }

    override suspend fun getAllVideoPaths(jewelryId: Long): List<String> = dbQuery {
        getList(jewelryId, JewelryMediaTable.videoPath)
    }

    override suspend fun saveMediaPaths(jewelryId: Long, data: JewelryMediaData): Unit = dbQuery {
        JewelryMediaManager.new {
            this.jewelryId = jewelryId
            this.cropImgPath = data.cropImgPath
            this.fullImgPath = data.fullImgPath
            this.videoPath = data.videoImgPath
        }
    }
}
class JewelryMediaManager (id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<JewelryMediaManager>(JewelryMediaTable)

    var jewelryId by JewelryMediaTable.jewelryId
    var cropImgPath by JewelryMediaTable.cropImgPath
    var fullImgPath by JewelryMediaTable.fullImgPath
    var videoPath by JewelryMediaTable.videoPath
}