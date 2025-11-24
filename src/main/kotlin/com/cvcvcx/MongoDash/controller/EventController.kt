package com.cvcvcx.MongoDash.controller

import com.cvcvcx.MongoDash.dto.ApiResponse
import com.cvcvcx.MongoDash.dto.EventRequest
import com.cvcvcx.MongoDash.dto.EventResponse
import com.cvcvcx.MongoDash.entity.Event
import com.cvcvcx.MongoDash.service.EventService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventController(
    private val eventService: EventService
) {

    @PostMapping
    fun createEvent(@Valid @RequestBody req: EventRequest
    ): ResponseEntity<ApiResponse<Void>>{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    success = true,
                    message = "이벤트 저장 완료",
                    data = null
                )
            )
    }

    @GetMapping
    fun getEvents(
    ): ResponseEntity<ApiResponse<List<EventResponse>>>{
        val events = eventService.getEvents()
        val response = events.map {
            EventResponse(
                id = it.id,
                userId = it.userId,
                eventType = it.eventType,
                page = it.page,
                metadata = it.metadata,
                timestamp = it.timestamp.toString()
            )
        }
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                message = "이벤트 목록 조회 완료",
                data = response
            )
        )
    }
}