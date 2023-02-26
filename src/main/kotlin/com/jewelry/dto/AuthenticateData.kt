package com.jewelry.dto

import kotlinx.serialization.Serializable

@Serializable
data class UUIDData(
    val uuid: String
)

@Serializable
data class DeviceTypeData(
    val deviceType: String
)

@Serializable
data class AuthenticationResponseData(
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
