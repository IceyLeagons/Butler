package net.iceyleagons.butler.services

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.servlet.http.HttpServletRequest
import java.util.*

interface GatekeeperService {
    data class GatekeeperEntity(
        val id: String,
        val email: String,
        val displayName: String,
        val identities: List<GatekeeperIdentity>
    ) {

        fun getIdentityById(id: String): Optional<GatekeeperIdentity> {
            return Optional.ofNullable(identities.firstOrNull { it.providerId == id })
        }
    }

    fun getAuthorizeUri(): String
    fun getLinkUri(): String
    fun fetchInformation(request: HttpServletRequest): GatekeeperEntity
    data class GatekeeperIdentity(
            val providerId: String,
            val providerAccessToken: String,
            @JsonProperty("providerAccessTokenExpires") val providerAccessTokenExpiry: Long,
            @JsonProperty("data") val extraData: Map<String, Any>
    )
}