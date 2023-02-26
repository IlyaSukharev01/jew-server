package com.jewelry.dao.authentication.uuid

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object UUIDAuthTable : UUIDTable("uuid_auth") {
    val deviceType = varchar("device_type", length = 255)
    val ipAddress = varchar("ip_address", length = 255)
    val addedDate = date("added_date").default(LocalDate.now())
}