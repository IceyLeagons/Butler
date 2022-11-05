package net.iceyleagons.butler.services.impl

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import net.iceyleagons.butler.CalendarEvent
import net.iceyleagons.butler.client
import net.iceyleagons.butler.json
import net.iceyleagons.butler.services.GatekeeperService
import net.iceyleagons.butler.services.MicrosoftService
import okhttp3.Request
import org.springframework.stereotype.Service

@Service
class MicrosoftServiceImpl : MicrosoftService {

    override fun getEventsInAllCalendars(user: GatekeeperService.GatekeeperIdentity): List<CalendarEvent> {
        return with(user.providerAccessToken) {
            val list = ArrayList<CalendarEvent>()
            requestAllCalendars(this).map {
                requestCalendarEvents(this, it)
            }.forEach(list::addAll)
            list
        }
    }

    private fun requestCalendarEvents(token: String, calendarId: String): List<CalendarEvent> {
        @Serializable
        data class TimeEntry(val dateTime: String, val timeZone: String)
        @Serializable
        data class Location(val displayName: String)
        @Serializable
        data class Entry(val id: String, val subject: String, val start: TimeEntry, val end: TimeEntry, val location: Location, val bodyPreview: String)
        @Serializable
        data class Response(val values: List<Entry>)

        val req = Request.Builder()
            .get()
            .url("https://graph.microsoft.com/v1.0/me/calendars/$calendarId/events")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build()

        return json.decodeFromString<Response>(client.newCall(req).execute().body.string()).values.map {
            CalendarEvent(
                subject = it.subject,
                start = Instant.parse(it.start.dateTime),
                end = Instant.parse(it.end.dateTime),
                location = it.location.displayName,
                description = it.bodyPreview
            )
        }
    }

    private fun requestAllCalendars(token: String): List<String> {
        @Serializable
        data class Entry(val id: String)
        @Serializable
        data class Response(val values: List<Entry>)

        val req = Request.Builder()
            .get()
            .url("https://graph.microsoft.com/v1.0/me/calendars")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build()

        return json.decodeFromString<Response>(client.newCall(req).execute().body.string()).values.map {
            it.id
        }
    }
}