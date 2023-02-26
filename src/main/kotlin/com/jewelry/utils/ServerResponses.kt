package com.jewelry.utils

object ServerResponses {
    //First letter always E - Exception
    //Second letter: E - Exposed;  T - Token; RI - Request Information;

    const val itemNotFound                  = "Item not found"
    const val uuidNotFound                  = "Uuid not found"
    const val uuidBadFormat                 = "Uuid bad format"
    const val tokenUUIDInfoMissing          = "Token information missing"
    const val tokenTypeInfoMissing          = "Token type info missing"
    const val tokenInvalidOrExpired         = "Token invalid or expired"
    const val suchTypeIsNotExists           = "Such type is not exists"
    const val itemNotFoundCode              = "EE01"
    const val uuidNotFoundCode              = "EE02"
    const val uuidBadFormatCode             = "ERI1"
    const val tokenUUIDInfoMissingCode      = "ET1"
    const val tokenTypeInfoMissingCode      = "ET2"
    const val tokenInvalidOrExpiredCode     = "ET3"
    const val suchTypeIsNotExistsCode       = "EE03"
}