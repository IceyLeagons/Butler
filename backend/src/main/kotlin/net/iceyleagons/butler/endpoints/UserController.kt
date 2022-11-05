package net.iceyleagons.butler.endpoints

import jakarta.servlet.http.HttpServletRequest
import net.iceyleagons.butler.services.GatekeeperService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.security.MessageDigest

@Controller
class UserController(val gatekeeperService: GatekeeperService) {

    @GetMapping("/api/me")
    fun getMe(request: HttpServletRequest): Me {
        return with(gatekeeperService.fetchInformation(request)) {
            Me(
                gravatarUrl = "https://www.gravatar.com/avatar/${email.md5()}",
                firstName = displayName,
                email = email,
                connectedServices = identities.map {
                    it.providerId
                }
            )
        }
    }

    private fun String.md5(): String {
        fun hex(array: ByteArray): String {
            val sb = StringBuffer()
            for (i in array.indices) {
                sb.append(
                    Integer.toHexString(
                        (array[i]
                            .toInt()
                                and 0xFF) or 0x100
                    ).substring(1, 3)
                )
            }
            return sb.toString()
        }

        return hex(MessageDigest.getInstance("MD5").digest(toByteArray(charset("CP1252"))))
    }

    data class Me(val gravatarUrl: String, val firstName: String, val email: String, val connectedServices: List<String>)

}