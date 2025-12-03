package com.cvcvcx.MongoDash.dto

import com.cvcvcx.MongoDash.entity.EventType
import com.cvcvcx.MongoDash.entity.TrackingEvent
import java.time.Instant

data class TrackingEventDto(
    val sessionId: String,
    val pageUrl: String,
    val eventType: EventType,
    val timestamp: Instant? = null,
    val viewportWidth: Int? = null,
    val viewportHeight: Int? = null,
    val xRatio: Double? = null,
    val yRatio: Double? = null,
    val durationMs: Long? = null,
    val targetSelector: String? = null

) {
    fun toEntity(): TrackingEvent {
        return TrackingEvent(
            sessionId = this.sessionId,
            pageUrl = this.pageUrl,
            eventType = this.eventType,
            viewportWidth = this.viewportWidth,
            viewportHeight = this.viewportHeight,
            xRatio = this.xRatio,
            yRatio = this.yRatio,
            durationMs = this.durationMs,
            targetSelector = this.targetSelector,
            timestamp = this.timestamp ?: Instant.now()
        )
    }
}
