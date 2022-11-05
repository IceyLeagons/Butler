package net.iceyleagons.butler.endpoints

import jakarta.servlet.http.HttpServletRequest
import net.iceyleagons.butler.services.GatekeeperService
import net.iceyleagons.butler.services.GooglePlacesService
import net.iceyleagons.butler.services.YoutubeService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import kotlin.jvm.optionals.getOrNull

@Controller
class RecommendationController(val gatekeeperService: GatekeeperService, val youtubeService: YoutubeService, val googlePlacesService: GooglePlacesService) {

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

}