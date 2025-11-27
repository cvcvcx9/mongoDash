package com.cvcvcx.MongoDash.dto

import com.cvcvcx.MongoDash.entity.EventType
import com.cvcvcx.MongoDash.entity.TrackingEvent

data class TrackingEventDto(
    val sessionId: String,
    val pageUrl: String,
    val eventType: EventType,
    val x: Int? = null,
    val y: Int? = null,
    val scrollY: Int? = null,
    val durationMs: Long? = null,
    val targetSelector: String? = null
) {
    fun toEntity(): TrackingEvent {
        return TrackingEvent(
            sessionId = this.sessionId,
            pageUrl = this.pageUrl,
            eventType = this.eventType,
            x = this.x,
            y = this.y,
            scrollY = this.scrollY,
            durationMs = this.durationMs,
            targetSelector = this.targetSelector
        )
    }
}
