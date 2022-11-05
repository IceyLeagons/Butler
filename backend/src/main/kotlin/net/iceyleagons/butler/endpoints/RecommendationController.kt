package net.iceyleagons.butler.endpoints

import jakarta.servlet.http.HttpServletRequest
import kotlinx.datetime.Clock
import net.iceyleagons.butler.services.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import kotlin.jvm.optionals.getOrNull
import kotlin.time.Duration.Companion.days

@Controller
class RecommendationController(val gatekeeperService: GatekeeperService, val youtubeService: YoutubeService, val googlePlacesService: GooglePlacesService, val spotifyService: SpotifyService, val seatgeekService: SeatgeekService) {

    @OptIn(ExperimentalStdlibApi::class)
    @GetMapping("/api/me/recommendations/youtube")
    fun getYoutubeRecommendations(request: HttpServletRequest): List<YoutubeService.Video> {
        return with(gatekeeperService.fetchInformation(request)) {
            val google = getIdentityById("google").getOrNull() ?: throw Exception("No linked Google account!")
            youtubeService.getRecommendedVideos(google)
        }
    }

    @GetMapping("/api/me/recommendations/places")
    fun getPlaceRecommendations(@RequestParam("lat") latitude: Double, @RequestParam("lng") longitude: Double): List<GooglePlacesService.Spot> {
        return googlePlacesService.getPlacesNear(latitude, longitude, null)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @GetMapping("/api/me/recommendations/concerts")
    fun getConcertRecommendations(request: HttpServletRequest, @RequestParam("lat") latitude: Double, @RequestParam("lng") longitude: Double): List<SeatgeekService.Concert> {
        return with(gatekeeperService.fetchInformation(request)) {
            val spotify = getIdentityById("spotify").getOrNull() ?: throw Exception("No Spotify account linked!")
            val artists = spotifyService.getMostListenedToSeatgeekArtists(spotify)

            seatgeekService.getConcerts(
                SeatgeekService.CityFilter(googlePlacesService.reverseGeocode(latitude, longitude)),
                SeatgeekService.TimeStartFilter(Clock.System.now()),
                SeatgeekService.TimeEndFilter(Clock.System.now().plus(7.days)),
                SeatgeekService.ArtistFilter(artists[0])
            )
        }
    }

}