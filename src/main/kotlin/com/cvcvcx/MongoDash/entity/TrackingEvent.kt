package com.cvcvcx.MongoDash.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document(collection = "tracking_events")
// @CompoundIndexes(
//     // 특정 세션의 특정 페이지 활동을 빠르게 조회하기 위한 복합 인덱스
//     CompoundIndex(name = "idx_session_page", def = "{'session_id': 1, 'page_url': 1}"),
//     // 특정 페이지에서 특정 타입의 이벤트(예: 클릭)만 모아 차트를 그릴 때 용도
//     CompoundIndex(name = "idx_page_type", def = "{'page_url': 1, 'event_type': 1}")
// )
open class TrackingEvent(
    @Id
    val id: String? = null,

    @Field("session_id")
    val sessionId: String,

    @Field("page_url")
    val pageUrl: String,

    @Field("event_type")
    val eventType: EventType,

    @Field("viewport_width")
    val viewportWidth: Int? = null,

    @Field("viewport_height")
    val viewportHeight: Int? = null,

    @Field("x_ratio")
    val xRatio: Double? = null,

    @Field("y_ratio")
    val yRatio: Double? = null,

    @Field("duration_ms")
    val durationMs: Long? = null,

    @Field("target_selector")
    val targetSelector: String? = null,

    val timestamp: Instant = Instant.now()
)

enum class EventType {
    CLICK, MOVE, SCROLL, RESIZE, HOVER, DWELL
}
