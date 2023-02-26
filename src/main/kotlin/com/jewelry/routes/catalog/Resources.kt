package com.jewelry.routes.catalog

import io.ktor.resources.*

@Resource("/catalog")
class Catalog (val orderBy: String? = null, val limit: Int = Int.MAX_VALUE, val offset: Long = 0) {
    @Resource("{type}")
    class Type(val parent: Catalog = Catalog(), val type: String)
}