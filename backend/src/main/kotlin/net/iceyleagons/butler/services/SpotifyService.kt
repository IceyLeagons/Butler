package net.iceyleagons.butler.services

interface SpotifyService {

    fun getMostListenedToArtists(user: GatekeeperService.GatekeeperIdentity): List<Artist>

    fun getMostListenedToSeatgeekArtists(user: GatekeeperService.GatekeeperIdentity): List<SeatgeekService.Artist>

    data class Artist(val name: String, val id: String)

}