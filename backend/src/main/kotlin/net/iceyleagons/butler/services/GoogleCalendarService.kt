package net.iceyleagons.butler.services

import net.iceyleagons.butler.CalendarEvent

interface GoogleCalendarService {

    fun getCalendarEvents(user: GatekeeperService.GatekeeperEntity.GatekeeperIdentity): List<CalendarEvent>

}