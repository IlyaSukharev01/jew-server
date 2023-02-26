package com.jewelry.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import java.util.*

interface JwtCooker {
    fun buildAccessJwt(uuid: String): String
    fun buildRefreshJwt(uuid: String): String
    fun verifyJwt(): JWTVerifier
}

class JwtCookerIMPL : JwtCooker {
    private val config = HoconApplicationConfig(ConfigFactory.load())

    private val secret = config.property("jwt.secret").getString()
    private val issuer = config.property("jwt.issuer").getString()
    private val audience = config.property("jwt.audience").getString()
    private val refreshExpireTime = config.property("jwt.refreshExpireTime").getString().toLong()
    private val accessExpireTime = config.property("jwt.accessExpireTime").getString().toLong()


    override fun buildAccessJwt(uuid: String): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("uuid", uuid)
        .withExpiresAt(Date(System.currentTimeMillis() + accessExpireTime))
        .sign(Algorithm.HMAC256(secret))


    override fun buildRefreshJwt(uuid: String): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("uuid", uuid)
        .withClaim("type", "refresh")
        .withExpiresAt(Date(System.currentTimeMillis() + refreshExpireTime))
        .sign(Algorithm.HMAC256(secret))

    override fun verifyJwt(): JWTVerifier = JWT.require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()
}