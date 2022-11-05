package net.iceyleagons.butler.services

interface GooglePlacesService {

    fun getPlacesNear(lat: Double, lon: Double, type: String?): List<Spot>

    fun reverseGeocode(lat: Double, lon: Double): String

    data class Spot(val address: String, val type: String, val latitude: Double, val longitude: Double)

}