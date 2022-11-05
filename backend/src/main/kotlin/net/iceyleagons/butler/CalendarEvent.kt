package net.iceyleagons.butler

import kotlinx.datetime.Instant

data class CalendarEvent(val subject: String, val start: Instant, val end: Instant, val location: String? = null, val description: String? = null)
