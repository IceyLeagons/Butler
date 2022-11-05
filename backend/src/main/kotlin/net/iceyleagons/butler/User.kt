package net.iceyleagons.butler

import net.iceyleagons.butler.services.GatekeeperService

data class User(
    val rssFeeds: List<String>,

) {
    companion object {
        fun getUser(gatekeeperEntity: GatekeeperService.GatekeeperEntity): User {
            return User(listOf("https://szeged.hu/rss/feed.xml"))
        }
    }
}
