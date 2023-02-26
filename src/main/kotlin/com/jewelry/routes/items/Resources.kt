package com.jewelry.routes.items

import io.ktor.resources.*

@Resource("/item")
class JewelryItem {

    @Resource("{id}")
    class Id (val parent:JewelryItem = JewelryItem(), val id: Long) {

        @Resource("save")
        class Save (val parent: Id)
    }
}