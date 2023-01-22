package com.jewelry.plugins

import com.jewelry.dto.AuthenticationData
import com.jewelry.utils.ServerResponses.incorrectUUIDFormat
import com.jewelry.utils.ServerResponses.incorrectUUIDLength
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureValidator() {
    install(RequestValidation) {
        validate<AuthenticationData> {
            it.uuidToken.forEach { sym ->
                if (!Character.isDigit(sym) && !Character.isLetter(sym) && sym != '-') {
                    return@validate ValidationResult.Invalid(incorrectUUIDFormat)
                }
            }

            if (it.uuidToken.length != 36) ValidationResult.Invalid(incorrectUUIDLength)
            else ValidationResult.Valid
        }
    }
}