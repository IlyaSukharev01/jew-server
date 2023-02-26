package com.jewelry.dto

import kotlinx.serialization.Serializable

@Serializable
data class JewelryItemResponseData (
    val item: DetailedJewelryData,
)

@Serializable
data class DetailedJewelryData(
    val jewelryId: Long,
    val jewelryName: String,
    val price: Int,
    val baseMaterial: String,
    val weight: Double? = null,
    val gemMaterial: String? = null,
    val gemsCount: Int? = null,
    val probe: Int? = null,
    val isSavedByUser: Boolean,
    val jewelryFullImagesPaths: List<String>,
    val jewelryVideoPaths: List<String>,
)