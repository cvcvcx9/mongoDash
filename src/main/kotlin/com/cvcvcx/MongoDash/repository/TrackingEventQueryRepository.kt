package com.cvcvcx.MongoDash.repository

import com.cvcvcx.MongoDash.entity.EventType
import com.cvcvcx.MongoDash.entity.TrackingEvent
import org.springframework.data.mongodb.core.MongoTemplate

interface TrackingEventQueryRepository{
    fun findBySessionIdAndUrlAndTypes(sessionId: String,url: String, types: List<EventType>): List<TrackingEvent>

}