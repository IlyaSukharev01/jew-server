package com.jewelry.dao.jewelry_media

import org.jetbrains.exposed.sql.Table

@ExperimentalUnsignedTypes
object JewelryMediaTable : Table("jewelry_media") {
    private val noteId = ulong("note_id").autoIncrement()
    val jewId = ulong("jew_id")
    val cropImgPath = varchar("crop_img_url", length = 255).nullable()
    val fullImgPath = varchar("full_img_url", length = 255).nullable()
    val videoPath = varchar("video_url", length = 255).nullable()

    override val primaryKey = PrimaryKey(noteId)
}