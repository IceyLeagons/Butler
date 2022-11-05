package net.iceyleagons.butler.services

import kotlinx.datetime.Instant

interface SeatgeekService {

    @Deprecated("use [#getConcerts] instead.")
    fun getConcertsInCity(city: String): List<Concert>

    @Deprecated("use [#getConcerts] instead.")
    fun getConcertsByArtist(artistName: String): List<Concert>

    @Deprecated("use [#getConcerts] instead.")
    fun getConcertsByTime(time: Instant, range: Int): List<Concert>

    fun getArtistByName(artist: String): Artist

    fun getConcerts(vararg filters: Filter): List<Concert>

    data class CityFilter(val city: String) : Filter("venue.city", city)

    data class ArtistFilter(val artist: Artist) : Filter("performer.id", artist.id.toString())

    data class TimeStartFilter(val start: Instant) : Filter("datetime_utc.gte", start.toString())

    data class TimeEndFilter(val end: Instant) : Filter("datetime_utc.lte", end.toString())

    open class Filter(val parameter: String, val value: String)

    data class Artist(val id: Int, val name: String, val slug: String)

    data class Concert(val name: String, val performer: String, val location: String, val time: Instant, val averagePrice: Int, val lowestPrice: Int)

}