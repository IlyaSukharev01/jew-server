package com.jewelry.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationData(
    val uuid: String
)

@Serializable
data class AuthenticationResponseData(
    val message: String,
    val uuid: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
