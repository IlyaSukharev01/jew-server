package com.jewelry.dto

import kotlinx.serialization.Serializable

@Serializable
data class CatalogResponseData (
    val list: List<PreviewJewelryData>
)

@Serializable
data class PreviewJewelryData(
    val jewelryId: Long,
    val jewelryName: String,
    val price: Int,
    val baseMaterial: String,
    val gemMaterial: String? = null,
    val jewelryCropImagesPaths: List<String>,
    val isSavedByUser: Boolean,
)