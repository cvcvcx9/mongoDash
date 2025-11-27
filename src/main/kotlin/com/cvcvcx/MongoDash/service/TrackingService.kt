package com.cvcvcx.MongoDash.service

import com.cvcvcx.MongoDash.dto.TrackingEventDto
import com.cvcvcx.MongoDash.dto.UserSessionDto

interface TrackingService {
    fun startSession(dto: UserSessionDto): UserSessionDto
    fun trackEvents(dtos: List<TrackingEventDto>): Int
}
