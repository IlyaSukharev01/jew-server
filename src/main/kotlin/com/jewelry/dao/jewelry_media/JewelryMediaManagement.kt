package com.jewelry.dao.jewelry_media

import com.jewelry.dto.JewelryMediaData
import com.jewelry.utils.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

interface JewelryMediaManagement {
    suspend fun getAllCropImagePaths(jewId: ULong): List<String>
    suspend fun getAllFullImagePaths(jewId: ULong): List<String>
    suspend fun getAllVideoPaths(jewId: ULong): List<String>
    suspend fun saveMediaPaths(jewId: ULong, data: JewelryMediaData): Boolean
}

@ExperimentalUnsignedTypes
class JewelryMediaManagementIMPL : JewelryMediaManagement {
    private fun getList(jewId: ULong, expression: Column<String?>): List<String> {
        return JewelryMediaTable
            .slice(expression)
            .select {JewelryMediaTable.jewId eq jewId }
            .mapNotNull { it[expression] }
    }

    override suspend fun getAllCropImagePaths(jewId: ULong): List<String> = dbQuery {
        getList(jewId, JewelryMediaTable.cropImgPath)
    }

    override suspend fun getAllFullImagePaths(jewId: ULong): List<String> = dbQuery {
        getList(jewId, JewelryMediaTable.fullImgPath)
    }

    override suspend fun getAllVideoPaths(jewId: ULong): List<String> = dbQuery {
        getList(jewId, JewelryMediaTable.videoPath)
    }

    override suspend fun saveMediaPaths(jewId: ULong, data: JewelryMediaData): Boolean = dbQuery {
        if (data.cropImgPath == null && data.fullImgPath == null && data.videoImgPath == null) return@dbQuery false

        JewelryMediaTable.insert {
            it[JewelryMediaTable.jewId] = jewId
            it[cropImgPath] = data.cropImgPath
            it[fullImgPath] = data.fullImgPath
            it[videoPath] = data.videoImgPath
        }.resultedValues != null

    }
}