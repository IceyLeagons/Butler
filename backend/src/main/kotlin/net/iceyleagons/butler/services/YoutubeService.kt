package net.iceyleagons.butler.services

interface YoutubeService {
    fun getRecommendedVideos(user: GatekeeperService.GatekeeperIdentity): List<Video>

    data class Video(val url: String, val name: String, val thumbnail: String, val uploader: String)
}