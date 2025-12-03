package com.cvcvcx.MongoDash.repository

import com.cvcvcx.MongoDash.entity.EventType
import com.cvcvcx.MongoDash.entity.TrackingEvent
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TrackingEventRepository : MongoRepository<TrackingEvent, String> {
    // 1. 특정 세션의 모든 행동 조회 (유저 행동 흐름 분석용)
    fun findBySessionId(sessionId: String): List<TrackingEvent>
    
    // 2. 특정 페이지의 특정 이벤트(클릭 등)만 조회 (히트맵 시각화용)
    fun findByPageUrlAndEventType(pageUrl: String, eventType: EventType): List<TrackingEvent>

    // 3. 세션아이디에 해당하는 히트맵을 그리기 위해 행동 조회
    fun findBySessionIdAndPageUrlAndEventType(sessionId: String, pageUrl: String, eventType:EventType): List<TrackingEvent>
}
