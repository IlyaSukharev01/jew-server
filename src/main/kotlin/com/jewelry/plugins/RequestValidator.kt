package com.jewelry.plugins

import com.jewelry.dto.UUIDData
import com.jewelry.exceptions.RequestInformationExceptions
import com.jewelry.utils.ServerResponses.uuidBadFormat
import com.jewelry.utils.ServerResponses.uuidBadFormatCode
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import java.util.*

fun Application.configureValidator() {
    install(RequestValidation) {
        validate<UUIDData> {data ->
            try {
                UUID.fromString(data.uuid)
                ValidationResult.Valid
            } catch (e: IllegalArgumentException) {
                throw RequestInformationExceptions(uuidBadFormatCode, uuidBadFormat)
            }
        }
    }
}