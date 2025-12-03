package com.cvcvcx.MongoDash.controller

import com.cvcvcx.MongoDash.dto.ApiResponse
import com.cvcvcx.MongoDash.dto.HeatmapRequestDto
import com.cvcvcx.MongoDash.dto.HeatmapResponseDto
import com.cvcvcx.MongoDash.service.AnalyticsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/analytics")
class AnalyticsController (
    private val analyticsService: AnalyticsService
){
    @GetMapping("/heatmap")
    fun heatmap(
        @RequestParam sessionId: String,
        @RequestParam pageUrl: String,
        @RequestParam snapshotWidth: Int,
        @RequestParam snapshotHeight: Int,
        @RequestParam deviceType: String
    ): ResponseEntity<ApiResponse<HeatmapResponseDto>>{
        val resp = analyticsService.getHeatmapBySession(
            HeatmapRequestDto(sessionId,pageUrl,snapshotWidth,snapshotHeight,deviceType)
        )
        return ResponseEntity.ok(ApiResponse(success = true, message = "ok", data = resp))
    }
}