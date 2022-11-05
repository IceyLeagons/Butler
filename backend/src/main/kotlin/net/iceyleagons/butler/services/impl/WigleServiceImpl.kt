package net.iceyleagons.butler.services.impl

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import net.iceyleagons.butler.endpoints.WifiController
import net.iceyleagons.butler.json
import net.iceyleagons.butler.services.WigleService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.getForObject

@Service
class WigleServiceImpl(@Value("\${wigle.api.token}") wigleKey: String, restTemplateBuilder: RestTemplateBuilder) : WigleService {
    val wigleTemplate = restTemplateBuilder.defaultHeader("Authorization", "Basic $wigleKey").build()!!

    /**
     * A serializable data class containing information about Wigle-provided networks.
     *
     * @author TOTHTOMI, Gabe
     * @version 1.0.0
     * @since Oct. 21, 2022
     */
    @Serializable
    data class WigleResponse(
        /**
         * Whether the request was successful or not.
         */
        val success: Boolean,

        /**
         * The amount of results before the filtering.
         */
        val totalResults: Int,

        /**
         * The (starting from 1) index of the first element.
         */
        val first: Int,

        /**
         * The (yet again, starting from 1) index of the last element.
         */
        val last: Int,

        /**
         * The amount of results after filtering.
         */
        val resultCount: Int,

        /**
         * All the networks that are valid to the filtering rules.
         */
        val results: Array<WigleNetwork>
    ) {
        /**
         * A serializable data class containing information about a Wigle-provided network.
         *
         * @author TOTHTOMI, Gabe
         * @version 1.0.0
         * @since Oct. 21, 2022
         */
        @kotlinx.serialization.Serializable
        data class WigleNetwork(
            /**
             * The latitude that this network was discovered on.
             */
            val trilat: Float,
            /**
             * The longitude that this network was discovered on.
             */
            val trilong: Float,
            /**
             * The SSID with which this network was found.
             */
            val ssid: String?,
            /**
             * The QoS number that was caught upon the discovering of this network.
             *
             * A number ranging from 0 to 7 (inclusive, 0..7)
             */
            val qos: Int?,
            /**
             * The type of this network.
             *
             * Can be BT, Cell or wifi
             */
            val type: String?,

            /**
             * If this network is possibly a freenet - public wifi that is accessible by anyone.
             *
             * Possible values include: 'Y', 'N', '?'
             */
            val freenet: String
        )
    }

    override fun findWifisNear(latitude: Double, longitude: Double, public: Boolean): List<WifiController.Wifi> {
        fun getWigleWifis(lat1: Double, lat2: Double, long1: Double, long2: Double): WigleResponse =
            json.decodeFromString(wigleTemplate.getForObject("https://api.wigle.net/api/v2/network/search?onlymine=false&latrange1=$lat1&latrange2=$lat2&longrange1=$long1&longrange2=$long2&lastupdt=20210101${if (public) "&freenet=true" else ""}&paynet=false"))

        val range = 0.036

        val lat1 = latitude - range
        val lat2 = latitude + range

        val long1 = longitude - range
        val long2 = longitude + range

        return getWigleWifis(lat1, lat2, long1, long2).results.map {
            WifiController.Wifi(it.ssid, it.freenet == "Y", it.trilat.toDouble(), it.trilong.toDouble())
        }
    }
}