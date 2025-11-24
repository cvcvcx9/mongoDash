package com.cvcvcx.MongoDash.service

import com.cvcvcx.MongoDash.dto.EventRequest
import com.cvcvcx.MongoDash.entity.Event
import com.cvcvcx.MongoDash.repository.EventRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class EventService(
    private val eventRepository: EventRepository
) {

    fun save(req:EventRequest): Event{
        val event= Event(
            userId = req.userId,
            eventType = req.eventType,
            page = req.page,
            metadata = req.metadata,
            timestamp = Instant.now(),
        )
        return eventRepository.save(event)
    }

    fun getEvents(): List<Event>{
        return eventRepository.findAll()
    }
}