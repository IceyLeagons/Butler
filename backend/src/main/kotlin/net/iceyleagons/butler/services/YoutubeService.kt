package net.iceyleagons.butler.services

interface YoutubeService {
    fun getRecommendedVideos(user: GatekeeperService.GatekeeperEntity.GatekeeperIdentity): List<Video>

    data class Video(val url: String, val name: String, val thumbnail: String, val uploader: String)
}