package net.iceyleagons.butler.services.impl

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import net.iceyleagons.butler.execute
import net.iceyleagons.butler.services.GatekeeperService
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GatekeeperServiceImpl(@Value("\${gatekeeper.client_id}") val clientId: String, @Value("\${gatekeeper.client_secret}") val clientSecret: String, @Value("\${gatekeeper.redirect_uri}") val redirectUri: String, @Value("\${gatekeeper.host}") val host: String) : GatekeeperService {

        val objectMapper = ObjectMapper()

    override fun getAuthorizeUri(): String = "${host}/authorize?client_id=${clientId}&redirect_uri=${redirectUri}"
    override fun getLinkUri(): String = "${host}/link?client_id=${clientId}&redirect_uri=${redirectUri}" // + token (on frontend side)

    override fun fetchInformation(request: HttpServletRequest): GatekeeperService.GatekeeperEntity {
        val accessToken = request.getHeader("Authorization")
        val okHttpRequest = Request.Builder()
            .url("${host}/api/currentUser?client_secret=${clientSecret}")
            .header("Authorization", accessToken)
            .build()

        return okHttpRequest.execute().toEntity()
    }

    private fun String.toEntity(): GatekeeperService.GatekeeperEntity = objectMapper.readValue(this, GatekeeperService.GatekeeperEntity::class.java)
}