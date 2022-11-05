package net.iceyleagons.butler.services.impl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.datetime.*
import net.iceyleagons.butler.execute
import net.iceyleagons.butler.services.RSSService
import okhttp3.Request
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class RSSServiceImpl : RSSService {
    @OptIn(ExperimentalStdlibApi::class)
    override fun parseRSS(link: String): RSSService.RSSResponse {
        val req = Request.Builder()
            .get()
            .url(link)
            .build()
        /*val rss = RssReader().read(req.execute().byteInputStream(Charset.defaultCharset()))
        rss.map { item ->
            RSSService.RSSResponse.Item(
                item.title.getOrNull() ?: "",
                item.link.getOrNull() ?: "",
                item.pubDateZonedDateTime.getOrNull()?.toInstant()?.let {
                    Instant.fromEpochMilliseconds(it.toEpochMilli())
                } ?: Clock.System.now(),
                item.description.getOrNull() ?: ""
            )
        }*/
        val xmlMapper = XmlMapper().registerKotlinModule()
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
        return with(xmlMapper.readValue(req.execute(), rss::class.java).channel) {
            RSSService.RSSResponse(title, link, image.url, items.map {
                RSSService.RSSResponse.Item(
                    it.title,
                    it.link,
                    SimpleDateFormat("EEE, dd MMM YYYY HH:mm:ss zzz", Locale("en", "US")).parse(it.pubDate).toInstant().toKotlinInstant(),
                    it.description ?: ""
                )
            })
        }
    }

    @JacksonXmlRootElement
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class rss(val channel: Channel) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Channel(val title: String, val link: String, val image: Image, @JacksonXmlElementWrapper(useWrapping = false) @JacksonXmlProperty(localName = "item") val items: List<Item>) {
            @JsonIgnoreProperties(ignoreUnknown = true)
            data class Image(val url: String)
            @JsonIgnoreProperties(ignoreUnknown = true)
            data class Item(val title: String, val link: String, val pubDate: String, @JacksonXmlCData val description: String?)
        }
    }
}