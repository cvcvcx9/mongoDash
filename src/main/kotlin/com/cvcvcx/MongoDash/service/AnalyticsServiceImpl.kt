package com.cvcvcx.MongoDash.service

import com.cvcvcx.MongoDash.dto.HeatmapPoint
import com.cvcvcx.MongoDash.dto.HeatmapRequestDto
import com.cvcvcx.MongoDash.dto.HeatmapResponseDto
import com.cvcvcx.MongoDash.entity.EventType
import com.cvcvcx.MongoDash.repository.TrackingComplexRepository
import com.cvcvcx.MongoDash.repository.TrackingEventRepository

class AnalyticsServiceImpl(
    val trackingComplexRepository: TrackingComplexRepository
) : AnalyticsService {
    override fun getHeatmapBySession(req: HeatmapRequestDto): HeatmapResponseDto {
//        이동만 한정해서 가져오기보다는

        val eventTypes = listOf(EventType.MOVE,EventType.CLICK)
        val events = trackingComplexRepository.findBySessionIdAndUrlAndTypes(req.sessionId,req.pageUrl,eventTypes)

        val counts = events.mapNotNull { ev ->
            val xRatio = ev.xRatio ?: return@mapNotNull null
            val yRatio = ev.yRatio ?: return@mapNotNull null
            val x = (xRatio * req.snapshotWidth).toInt().coerceAtLeast(0)
            val y = (xRatio * req.snapshotHeight).toInt().coerceAtLeast(0)
            x to y
        }.groupingBy { it }.eachCount()

        val points = counts.map { (coord,count)->
            HeatmapPoint(x=coord.first,y = coord.second, weight = count)
        }
        val maxValue = points.maxOfOrNull { it.weight } ?: 0
        return HeatmapResponseDto(points,maxValue)

    }
}