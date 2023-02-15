package com.jewelry.dto

import kotlinx.serialization.Serializable

@Serializable
data class CatalogResponseData (
    val message: String,
    val list: List<PreviewJewelryData>? = null,
)

@Serializable
data class PreviewJewelryData(
    val jewId: ULong,
    val jewName: String,
    val price: UInt,
    val baseMaterial: String,
    val gemMaterial: String? = null,
    val jewCropImagesPaths: List<String>,
    val isSavedByUser: Boolean,
)