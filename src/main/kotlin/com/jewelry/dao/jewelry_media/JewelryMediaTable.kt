package com.jewelry.dao.jewelry_media

import org.jetbrains.exposed.dao.id.LongIdTable

object JewelryMediaTable : LongIdTable("jewelry_media") {
    val jewelryId = long("jew_id")
    val cropImgPath = varchar("crop_img_url", length = 255).nullable()
    val fullImgPath = varchar("full_img_url", length = 255).nullable()
    val videoPath = varchar("video_url", length = 255).nullable()
}