package com.cvcvcx.MongoDash.service

import com.cvcvcx.MongoDash.dto.TrackingEventDto
import com.cvcvcx.MongoDash.dto.UserSessionDto
import com.cvcvcx.MongoDash.repository.TrackingEventRepository
import com.cvcvcx.MongoDash.repository.UserSessionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TrackingServiceImpl(
    private val userSessionRepository: UserSessionRepository,
    private val trackingEventRepository: TrackingEventRepository
) : TrackingService {

    @Transactional
    override fun startSession(dto: UserSessionDto): UserSessionDto {
        // 이미 존재하는 세션인지 확인 (중복 방지)
        val existingSession = userSessionRepository.findBySessionId(dto.sessionId)
        if (existingSession != null) {
            return dto // 이미 있으면 그대로 반환 (또는 기존 정보 반환)
        }
        
        userSessionRepository.save(dto.toEntity())
        return dto
    }

    override fun trackEvents(dtos: List<TrackingEventDto>): Int {
        // DTO List -> Entity List 변환
        val entities = dtos.map { it.toEntity() }
        
        val saved = trackingEventRepository.saveAll(entities)
        return saved.size
    }
}
