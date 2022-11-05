package net.iceyleagons.butler.services.impl

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import net.iceyleagons.butler.client
import net.iceyleagons.butler.execute
import net.iceyleagons.butler.json
import net.iceyleagons.butler.services.SeatgeekService
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import kotlin.time.Duration.Companion.days

@Service
class SeatgeekServiceImpl(@Value("\${seatgeek.api.client_id}") val clientId: String, @Value("\${seatgeek.api.client_secret}") val secret: String) : SeatgeekService {

    @Deprecated("use [#getConcerts] instead.")
    override fun getConcertsInCity(city: String): List<SeatgeekService.Concert> {
        val req = Request.Builder()
            .get()
            .url("https://api.seatgeek.com/2/events".toHttpUrl().newBuilder()
                .addQueryParameter("venue.city", city.replace(' ', '+'))
                .build())
            .addHeader("Authorization", Credentials.basic(clientId, secret))
            .build()
        return req.execute().asConcerts()
    }

     @Serializable
     data class Meta(val total: Int, val took: Int, val page: Int, @SerialName("per_page") val perPage: Int)
     @Serializable
     data class Link(val id: String, val url: String, val provider: String)
     @Serializable
     data class Performer(val type: String, val id: Int, val links: List<Link>, val slug: String, @SerialName("short_name") val shortName: String)
     override fun getArtistByName(artist: String): SeatgeekService.Artist? {
         @Serializable
         data class PerformerResponse(val performers: List<Performer>, val meta: Meta)

         val req = Request.Builder()
            .get()
            .url("https://api.seatgeek.com/2/performers".toHttpUrl().newBuilder()
                .addQueryParameter("q", artist.replace(' ', '+'))
                .build())
            .addHeader("Authorization", Credentials.basic(clientId, secret))
            .build()

         try {
             return with(json.decodeFromString<PerformerResponse>(client.newCall(req).execute().body.string()).performers.filter {
                 it.type == "band"
             }[0]) {
                 SeatgeekService.Artist(id, shortName, slug)
             }
         } catch(e: NullPointerException) {
             return null
         }
    }

    @Serializable
    data class Location(@SerialName("lat") val latitude: Double, @SerialName("lon") val longitude: Double)
    @Serializable
    data class Venue(val state: String? = null, val name: String, val location: Location, val city: String, val address: String, @SerialName("extended_address") val extendedAddress: String)
    @Serializable
    data class Stats(@SerialName("listing_count") val listingCount: Int, @SerialName("average_price") val averagePrice: Int, @SerialName("lowest_price") val lowestPrice: Int)
    @Serializable
    data class Event(val type: String, @SerialName("datetime_utc") val time: String, @SerialName("short_title") val shortTitle: String, val stats: Stats, val venue: Venue, val performers: List<Performer>)
    @Serializable
    data class EventsResponse(val events: List<Event>, val meta: Meta)

    @Deprecated("use [#getConcerts] instead.")
    override fun getConcertsByArtist(artistName: String): List<SeatgeekService.Concert> {
        val artist = getArtistByName(artistName)
        val req = Request.Builder()
            .get()
            .url("https://api.seatgeek.com/2/events".toHttpUrl().newBuilder()
                .addQueryParameter("performers.id", artist?.id?.toString())
                .build())
            .addHeader("Authorization", Credentials.basic(clientId, secret))
            .build()

        return req.execute().asConcerts()
    }

    @Deprecated("use [#getConcerts] instead.")
    override fun getConcertsByTime(time: Instant, range: Int): List<SeatgeekService.Concert> {
        val req = Request.Builder()
            .get()
            .url("https://api.seatgeek.com/2/events".toHttpUrl().newBuilder()
                .addQueryParameter("timezone_utc.gte", time.minus(range.days).toString())
                .addQueryParameter("timezone_utc.lte", time.plus(range.days).toString())
                .build())
            .addHeader("Authorization", Credentials.basic(clientId, secret))
            .build()

        return req.execute().asConcerts()
    }

    override fun getConcerts(vararg filters: SeatgeekService.Filter): List<SeatgeekService.Concert> {
        val req = Request.Builder()
            .get()
            .url("https://api.seatgeek.com/2/events".toHttpUrl().newBuilder()
                .let {
                    var a = it
                    for (filter in filters) {
                        a = a.addQueryParameter(filter.parameter, filter.value)
                    }
                    a
                }
                .build())
            .addHeader("Authorization", Credentials.basic(clientId, secret))
            .build()

        return req.execute().asConcerts()
    }

    private fun String.asConcerts(): List<SeatgeekService.Concert> = with(json.decodeFromString<EventsResponse>(this)) {
        events.map {
            SeatgeekService.Concert(it.shortTitle, it.performers[0].shortName, it.venue.address, Instant.parse(it.time), it.stats.averagePrice, it.stats.lowestPrice)
        }
    }
}

fun main(args: Array<String>) {
    RSSServiceImpl().parseRSS("https://szeged.hu/rss/feed.xml").also(::println)
/*SeatgeekServiceImpl(
        "MjEyMTg4NTJ8MTY2NzYzMzc3NC42NDgyNjU",
        "c1c0a5d6ae94b4b981e79863854021e2487ae9d3d167af7be19fd23cb72d6327"
    ).let {
        it.getConcertsInCity("Budapest")
        it.getConcertsByArtist("Adele")
    }*/
}