package net.iceyleagons.butler.endpoints

import jakarta.servlet.http.HttpServletRequest
import net.iceyleagons.butler.CalendarEvent
import net.iceyleagons.butler.services.GatekeeperService
import net.iceyleagons.butler.services.GoogleCalendarService
import net.iceyleagons.butler.services.MicrosoftService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull

@RestController
class CalendarController(val gatekeeper: GatekeeperService, val googleCalendarService: GoogleCalendarService, val microsoftService: MicrosoftService) {

    @OptIn(ExperimentalStdlibApi::class)
    @GetMapping("/api/me/calendar.events")
    fun getCalendar(request: HttpServletRequest): List<CalendarEvent> {
        return with(gatekeeper.fetchInformation(request)) {
            val google = getIdentityById("google").getOrNull()
            val microsoft = getIdentityById("microsoft").getOrNull()

            val events = ArrayList<CalendarEvent>(4)
            if (google())
                events += googleCalendarService.getCalendarEvents(google!!)
            if (microsoft())
                events += microsoftService.getEventsInAllCalendars(microsoft!!)
            events
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @GetMapping("/api/me/calendar.events/microsoft")
    fun getMicrosoftCalendar(request: HttpServletRequest): List<CalendarEvent> {
        return with(gatekeeper.fetchInformation(request)) {
            val microsoft = getIdentityById("microsoft").getOrNull() ?: throw Exception("No linked Microsoft account!")
            microsoftService.getEventsInAllCalendars(microsoft)
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @GetMapping("/api/me/calendar.events/google")
    fun getGoogleCalendar(request: HttpServletRequest): List<CalendarEvent> {
        return with(gatekeeper.fetchInformation(request)) {
            val google = getIdentityById("google").getOrNull() ?: throw Exception("No linked Google account!")
            microsoftService.getEventsInAllCalendars(google)
        }
    }

    operator fun GatekeeperService.GatekeeperEntity.GatekeeperIdentity?.invoke(): Boolean = this != null

}