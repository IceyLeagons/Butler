package net.iceyleagons.butler.endpoints

import net.iceyleagons.butler.services.WigleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class WifiController(val wigleService: WigleService) {

    @GetMapping("/api/me/wifi.near")
    fun getWifis(@RequestParam("lat") latitude: Double, @RequestParam("lng") longitude: Double): List<Wifi> {
        return wigleService.findWifisNear(latitude, longitude, false)
    }

    @GetMapping("/api/me/wifi.near/public")
    fun getPublicWifis(@RequestParam("lat") latitude: Double, @RequestParam("lng") longitude: Double): List<Wifi> {
        return wigleService.findWifisNear(latitude, longitude, true)
    }

    data class Wifi(val ssid: String?, val freenet: Boolean, val lat: Double, val lng: Double)

}