package net.iceyleagons.butler.services.impl

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import net.iceyleagons.butler.CalendarEvent
import net.iceyleagons.butler.gsonFactory
import net.iceyleagons.butler.services.GatekeeperService
import net.iceyleagons.butler.services.GoogleCalendarService
import org.springframework.stereotype.Service
import java.util.*

@Service
class GoogleCalendarServiceImpl : GoogleCalendarService {
    override fun getCalendarEvents(user: GatekeeperService.GatekeeperEntity.GatekeeperIdentity): List<CalendarEvent> {
        val eventResult = Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), gsonFactory, HttpCredentialsAdapter(GoogleCredentials.create(
            AccessToken(user.providerAccessToken, Date.from(Instant.fromEpochMilliseconds(user.providerAccessTokenExpiry).toJavaInstant()))
        ))).build().events()
            .list("primary")
            .setMaxResults(25)
            .setTimeMin(DateTime(System.currentTimeMillis()))
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        return eventResult.items.map {
            CalendarEvent(
                subject = it.summary,
                start = Instant.fromEpochMilliseconds(it.start.dateTime.value),
                end = Instant.fromEpochMilliseconds(it.end.dateTime.value),
                location = it.location,
                description = it.description
            )
        }
    }
}