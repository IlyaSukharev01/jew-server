package com.jewelry.dto

import kotlinx.serialization.Serializable

@Serializable
data class JewelryItemResponseData (
    val message: String,
    val item: DetailedJewelryData? = null,
)

@Serializable
data class DetailedJewelryData(
    val jewId: ULong,
    val jewName: String,
    val price: UInt,
    val baseMaterial: String,
    val weight: Double? = null,
    val gemMaterial: String? = null,
    val gemsCount: UInt? = null,
    val probe: UInt? = null,
    val isSavedByUser: Boolean,
    val jewFullImagesPaths: List<String>,
    val jewVideoPaths: List<String>,
)