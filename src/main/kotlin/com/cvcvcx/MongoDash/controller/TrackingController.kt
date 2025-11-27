package com.cvcvcx.MongoDash.controller

import com.cvcvcx.MongoDash.dto.ApiResponse
import com.cvcvcx.MongoDash.dto.TrackingEventDto
import com.cvcvcx.MongoDash.dto.UserSessionDto
import com.cvcvcx.MongoDash.service.TrackingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/track")
@CrossOrigin(origins = ["*"]) // 개발 편의용
class TrackingController(
    private val trackingService: TrackingService
) {

    // 1. 세션 시작
    @PostMapping("/session")
    fun startSession(@RequestBody sessionDto: UserSessionDto): ResponseEntity<ApiResponse<UserSessionDto>> {
        val savedSession = trackingService.startSession(sessionDto)
        return ResponseEntity.ok(ApiResponse(true, "Session started", savedSession))
    }

    // 2. 이벤트 전송
    @PostMapping("/events")
    fun trackEvents(@RequestBody events: List<TrackingEventDto>): ResponseEntity<ApiResponse<Int>> {
        val savedCount = trackingService.trackEvents(events)
        return ResponseEntity.ok(ApiResponse(true, "Events tracked", savedCount))
    }
}
