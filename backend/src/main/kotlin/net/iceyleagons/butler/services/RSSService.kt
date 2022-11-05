package net.iceyleagons.butler.services

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.iceyleagons.butler.InstantSerializer

interface RSSService {

    fun parseRSS(link: String): RSSResponse

    @Serializable
    data class RSSResponse(val title: String, val link: String, @SerialName("image_url") val imageUrl: String, val items: List<Item>) {
        @Serializable
        data class Item(val title: String, val link: String, @SerialName("published_at") @Serializable(with = InstantSerializer::class) val publishDate: Instant, val content: String)
    }

}