package net.iceyleagons.butler.services.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.PlacesApi
import com.google.maps.model.LatLng
import com.google.maps.model.PlaceType
import com.google.maps.model.RankBy
import net.iceyleagons.butler.services.GooglePlacesService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GooglePlacesServiceImpl(@Value("\${google.api.places.token}") val placesToken: String) : GooglePlacesService {
    private val apiContext by lazy {
        GeoApiContext.Builder()
            .apiKey(placesToken)
            .queryRateLimit(5)
            .build()
    }

    override fun getPlacesNear(lat: Double, lon: Double, type: String?): List<GooglePlacesService.Spot> {
        return PlacesApi.nearbySearchQuery(apiContext, LatLng(lat, lon)).radius(3_500).await().results.filter {
            it.types.contains("museum")
        /*arrayOf(
                PlaceType.AMUSEMENT_PARK,
                PlaceType.ART_GALLERY,
                PlaceType.BOWLING_ALLEY,
                PlaceType.CASINO,
                PlaceType.GYM,
                PlaceType.LIBRARY,
                PlaceType.MOVIE_THEATER,
                PlaceType.MUSEUM,
                PlaceType.SPA,
                PlaceType.STADIUM,
                PlaceType.ZOO
            ).any { type ->
                true //it.types.contains(type.name)
            }*/
        }.map {
            println(ObjectMapper().writeValueAsString(it))
            GooglePlacesService.Spot(
                address = it.vicinity,
                type = it.types.joinToString(separator = ", "),
                latitude = it.geometry.location.lat,
                longitude = it.geometry.location.lng
            )
        }
    }

    override fun reverseGeocode(lat: Double, lon: Double): String {
        return GeocodingApi.reverseGeocode(apiContext, LatLng(lat, lon)).await()[0].addressComponents[0].longName
    }
}