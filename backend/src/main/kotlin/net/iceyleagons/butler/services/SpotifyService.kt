package net.iceyleagons.butler.services

interface SpotifyService {

    fun getMostListenedToArtists(user: GatekeeperService.GatekeeperEntity.GatekeeperIdentity): List<Artist>

    fun getMostListenedToSeatgeekArtists(user: GatekeeperService.GatekeeperEntity.GatekeeperIdentity): List<SeatgeekService.Artist>

    data class Artist(val name: String, val id: String)

}