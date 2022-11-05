package net.iceyleagons.butler.services

import net.iceyleagons.butler.endpoints.WifiController

interface WigleService {

    fun findWifisNear(lat: Double, lon: Double, public: Boolean = false): List<WifiController.Wifi>

}