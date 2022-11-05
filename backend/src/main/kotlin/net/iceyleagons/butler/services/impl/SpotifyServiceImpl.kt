package net.iceyleagons.butler.services.impl

import net.iceyleagons.butler.services.GatekeeperService
import net.iceyleagons.butler.services.SeatgeekService
import net.iceyleagons.butler.services.SpotifyService
import org.springframework.stereotype.Service
import se.michaelthelin.spotify.SpotifyApi

@Service
class SpotifyServiceImpl(val seatgeekService: SeatgeekService) : SpotifyService {

    override fun getMostListenedToArtists(user: GatekeeperService.GatekeeperIdentity): List<SpotifyService.Artist> {
        val spotifyApi = SpotifyApi.builder().setAccessToken(user.providerAccessToken).build()
        return spotifyApi.usersTopArtists.build().execute().items.map {
            SpotifyService.Artist(name = it.name, id = it.id)
        }
    }

    override fun getMostListenedToSeatgeekArtists(user: GatekeeperService.GatekeeperIdentity): List<SeatgeekService.Artist> =
        getMostListenedToArtists(user).map {
            seatgeekService.getArtistByName(it.name)
        }.filterNotNull()

}