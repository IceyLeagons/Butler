package net.iceyleagons.butler.services.impl

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.services.youtube.YouTube
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import net.iceyleagons.butler.gsonFactory
import net.iceyleagons.butler.services.GatekeeperService
import net.iceyleagons.butler.services.YoutubeService
import org.springframework.stereotype.Service
import java.util.*

@Service
class YoutubeServiceImpl : YoutubeService {
    override fun getRecommendedVideos(user: GatekeeperService.GatekeeperEntity.GatekeeperIdentity): List<YoutubeService.Video> {
        val yt = YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), gsonFactory, HttpCredentialsAdapter(GoogleCredentials.create(
            AccessToken(user.providerAccessToken, Date.from(Instant.fromEpochMilliseconds(user.providerAccessTokenExpiry).toJavaInstant()))
        )))
            .setApplicationName("Butler")
            .build()

        val currChannel = yt.channels().list("contentDetails").setMine(true).execute().items[0].id
        val act = yt.activities().list("id,snippet,subscriberSnippet")
        act.channelId = currChannel
        act.maxResults = 5

        return act.execute().items.map {
            YoutubeService.Video(
                "https://www.youtube.com/watch?v=${it.id}",
                it.snippet.title,
                it.snippet.thumbnails.default.url,
                it.snippet.channelTitle
            )
        }
    }
}