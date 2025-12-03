package com.cvcvcx.MongoDash.service

import com.cvcvcx.MongoDash.dto.HeatmapPoint
import com.cvcvcx.MongoDash.dto.HeatmapRequestDto
import com.cvcvcx.MongoDash.dto.HeatmapResponseDto

interface AnalyticsService {
    fun getHeatmapBySession(req: HeatmapRequestDto): HeatmapResponseDto
}