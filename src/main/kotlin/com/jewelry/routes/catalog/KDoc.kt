package com.jewelry.routes.catalog

import com.jewelry.dto.CatalogResponseData
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.bkbn.kompendium.json.schema.definition.TypeDefinition
import io.bkbn.kompendium.oas.payload.Parameter
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.documentation() {
    install(NotarizedRoute()) {
        parameters = listOf (
            Parameter("orderBy", `in` = Parameter.Location.query, schema = TypeDefinition.STRING),
            Parameter("limit", `in` = Parameter.Location.query, schema = TypeDefinition.INT),
            Parameter("offset", `in` = Parameter.Location.query, schema = TypeDefinition.LONG),
            Parameter("type", `in` = Parameter.Location.path, schema = TypeDefinition.STRING)
        )

        get = GetInfo.builder {
            summary("Its getting all of the jewelries")
            description("Getting all jewelries or ordered by type. Each endpoint may contains some query params." +
                    " There`re orderBy, limit, offset.")
            response {
                responseCode(HttpStatusCode.OK)
                responseType<CatalogResponseData>()
            }
        }
    }
}