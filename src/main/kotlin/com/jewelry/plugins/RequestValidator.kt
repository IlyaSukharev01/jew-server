package com.jewelry.plugins

import com.jewelry.dto.AuthenticationData
import com.jewelry.utils.ServerResponses.incorrectUUIDFormat
import com.jewelry.utils.ServerResponses.incorrectUUIDLength
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidator() {
    install(RequestValidation) {
        validate<AuthenticationData> {
            it.uuid.forEach { sym ->
                if (!Character.isDigit(sym) && !Character.isLetter(sym) && sym != '-') {
                    return@validate ValidationResult.Invalid(incorrectUUIDFormat)
                }
            }
            if (it.uuid.length != 36)  ValidationResult.Invalid(incorrectUUIDLength)
            else                            ValidationResult.Valid
        }
    }
}