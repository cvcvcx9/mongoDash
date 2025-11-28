package com.cvcvcx.MongoDash.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "tracking_events")
// @CompoundIndexes(
//     // 특정 세션의 특정 페이지 행동을 빠르게 조회하기 위한 복합 인덱스
//     CompoundIndex(name = "idx_session_page", def = "{'session_id': 1, 'page_url': 1}"),
//     // 특정 페이지의 특정 타입 이벤트(예: 클릭)만 모아서 히트맵 그릴 때 사용
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
    val eventType: EventType, // CLICK, MOVE, SCROLL, RESIZE

    // 좌표 정보 (Nullable: 스크롤 이벤트엔 좌표가 없을 수 있음)
    val x: Int? = null,
    val y: Int? = null,

    @Field("scroll_y")
    val scrollY: Int? = null, // 스크롤 깊이

    @Field("duration_ms")
    val durationMs: Long? = null, // 머문 시간 (Hover 시)

    @Field("target_selector")
    val targetSelector: String? = null, // 클릭한 요소의 CSS Selector

    val timestamp: LocalDateTime = LocalDateTime.now()
)

enum class EventType {
    CLICK, MOVE, SCROLL, RESIZE, HOVER
}
