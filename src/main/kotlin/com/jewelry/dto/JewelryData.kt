package com.jewelry.dto

import java.time.LocalDate

data class JewelryData (
    val jewId: ULong,
    val jewName: String,
    val jewType: String,
    val price: UInt,
    val baseMaterial: String,
    val weight: Double? = null,
    val gemMaterial: String? = null,
    val gemsCount: UInt? = null,
    val probe: UInt? = null,
    val views: ULong = 0U,
    val addedDate: LocalDate? = null,
)

data class JewelryMediaData(
    val cropImgPath: String? = null,
    val fullImgPath: String? = null,
    val videoImgPath: String? = null,
)