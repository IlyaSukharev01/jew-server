package com.jewelry.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationData(
    val uuidToken: String
)

@Serializable
data class AuthenticationResponseData(
    val message: String,
    val uuidToken: String? = null,
)
