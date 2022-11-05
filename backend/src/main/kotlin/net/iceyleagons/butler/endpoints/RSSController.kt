package net.iceyleagons.butler.endpoints

import jakarta.servlet.http.HttpServletRequest
import net.iceyleagons.butler.User
import net.iceyleagons.butler.services.GatekeeperService
import net.iceyleagons.butler.services.RSSService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RSSController(val gatekeeperService: GatekeeperService, val rssService: RSSService) {

    @GetMapping("/api/me/rss.fetchAll")
    fun getRssFeeds(request: HttpServletRequest): List<RSSService.RSSResponse> {
        return with(gatekeeperService.fetchInformation(request)) {
            ArrayList<RSSService.RSSResponse>().let {
                it.addAll(User.getUser(this).rssFeeds.map { link ->
                    rssService.parseRSS(link)
                })

                it
            }
        }
    }

    @GetMapping("/api/me/rss.links")
    fun getRssLinks(request: HttpServletRequest): List<String> {
        return with(gatekeeperService.fetchInformation(request)) {
            User.getUser(this).rssFeeds
        }
    }

}