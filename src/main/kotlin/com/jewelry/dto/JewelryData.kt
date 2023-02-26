package com.jewelry.dto

import java.time.LocalDate

data class JewelryData (
    val jewelryId: Long? = null,
    val jewelryName: String,
    val jewelryType: Int,
    val price: Int,
    val baseMaterial: String,
    val weight: Double? = null,
    val gemMaterial: String? = null,
    val gemsCount: Int? = null,
    val probe: Int? = null,
    val views: Long = 0,
    val addedDate: LocalDate? = null,
)

data class JewelryMediaData(
    val cropImgPath: String? = null,
    val fullImgPath: String? = null,
    val videoImgPath: String? = null,
)