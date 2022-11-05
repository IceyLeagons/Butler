package net.iceyleagons.butler.endpoints

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SmartHomeController {

    @GetMapping("/api/me/appliances.list")
    fun getAppliances(request: HttpServletRequest): List<SmartAppliance> {
        return listOf(SmartLight, SmarterLight, SmartDog)
    }

    @PutMapping("/api/me/appliances.update")
    fun updateAppliance(request: HttpServletRequest, @RequestParam("object") objectName: String, @RequestParam("new") newValue: Int): Response {
        return Response(true)
    }

    data class Response(
        val success: Boolean,
        val reason: String? = null
    )

    open class SmartAppliance(
        val type: String,
        val controlType: String,
        val controlStatus: Int,
        @JsonProperty("image_url") val imageUrl: String,
        val name: String,
        val maker: String
    )

    object SmartLight : SmartAppliance(
        "light",
        "toggle",
        1,
        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Gluehlampe_01_KMJ.png/640px-Gluehlampe_01_KMJ.png",
        "Smart lightbulb",
        "Generic lightbulb manifacturer"
    )

    object SmarterLight : SmartAppliance(
        "light",
        "slider",
        57,
        "https://cdn.shopify.com/s/files/1/1853/1873/files/B1835FAVI-PNG_1600x.png?v=1613583394",
        "Smarter lightbulb",
        "Not-So Generic lightbulb manifacturer"
    )

    object SmartDog : SmartAppliance(
        "dog",
        "colorwheel",
        convertRGBToInt(200, 100, 0),
        "https://wesellrodrigueworks.com/wp-content/uploads/2019/06/bluedogalone1.png",
        "Rekku: the glowing dog",
        "Pukinmäki dog farm"
    )

    companion object {
        fun convertRGBToInt(r: Int, g: Int, b: Int): Int = r and 0x0ff shl 16 or (g and 0x0ff shl 8) or (b and 0x0ff)
        fun fromIntToRGB(rgb: Int): Triple<Int, Int, Int> {
            val red = rgb shr 16 and 0x0ff
            val green = rgb shr 8 and 0x0ff
            val blue = rgb and 0x0ff
            return Triple(red, green, blue)
        }
    }


}