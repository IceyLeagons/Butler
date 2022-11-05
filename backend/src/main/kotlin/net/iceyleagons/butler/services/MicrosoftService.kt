package net.iceyleagons.butler.services

import net.iceyleagons.butler.CalendarEvent

interface MicrosoftService {

    fun getEventsInAllCalendars(user: GatekeeperService.GatekeeperEntity.GatekeeperIdentity): List<CalendarEvent>

}