package com.cvcvcx.MongoDash.repository

import com.cvcvcx.MongoDash.entity.EventType
import com.cvcvcx.MongoDash.entity.TrackingEvent
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class TrackingEventQueryRepositoryImpl(
    val mongoTemplate: MongoTemplate
) : TrackingEventQueryRepository {

    override fun findBySessionIdAndUrlAndTypes(
        sessionId: String,
        url: String,
        types: List<EventType>
    ): List<TrackingEvent> {
        val query = Query(
            Criteria.where("sessionId").`is`(sessionId)
                .and("url").`is`(url)
                .and("event_type").`in`(types)
        )
        return mongoTemplate.find(query,TrackingEvent::class.java)
    }
}