package com.jewelry.exceptions

import kotlinx.serialization.Serializable

@Serializable
data class CustomExceptionsData (
    val code: String,
    val message: String,
    val optionalInfo: String,
)

abstract class CustomExceptions (private val code: String, private val msg: String, private val optionalInfo: String? = null) : Exception(msg) {
    fun getFullDescription(): CustomExceptionsData =
        CustomExceptionsData (
            code = code,
            message = msg,
            optionalInfo = optionalInfo ?: "-"
        )
}


class ExposedException (code: String, message: String, optionalInfo: String? = null):  CustomExceptions(code, message, optionalInfo)
class TokenExceptions (code: String, message: String, optionalInfo: String? = null) : CustomExceptions(code, message, optionalInfo)
class RequestInformationExceptions (code: String, message: String, optionalInfo: String? = null) : CustomExceptions(code, message, optionalInfo)