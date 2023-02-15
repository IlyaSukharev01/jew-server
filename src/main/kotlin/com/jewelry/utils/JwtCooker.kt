package com.jewelry.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.jewelry.dao.authentication.uuid.UUIDAuthManagement
import com.typesafe.config.ConfigFactory
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

interface JwtCooker {
    fun buildAccessJwt(uuid: String): String
    fun buildRefreshJwt(uuid: String): String
    fun verifyJwt(): JWTVerifier
    suspend fun isCredentialUseful(credential: JWTCredential): Boolean
}

class JwtCookerIMPL: JwtCooker, KoinComponent {
    private val config = HoconApplicationConfig(ConfigFactory.load())

    private val secret   = config.property("jwt.secret").getString()
    private val issuer   = config.property("jwt.issuer").getString()
    private val audience = config.property("jwt.audience").getString()

    private val refreshExpireTime = 86400000L
    private val accessExpireTime = 1800000L

    private val uuidAuthDao by inject<UUIDAuthManagement>()



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

    override suspend fun isCredentialUseful(credential: JWTCredential): Boolean {
        credential.payload.getClaim("uuid").asString()?.let {str ->
            try {
                UUID.fromString(str).let {uuid ->
                    return uuidAuthDao.isUUIDExists(uuid)
                }
            } catch (e: IllegalArgumentException) {
                return false
            }
        } ?: return false
    }
}