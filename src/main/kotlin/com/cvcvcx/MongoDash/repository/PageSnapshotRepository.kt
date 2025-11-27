package com.cvcvcx.MongoDash.repository

import com.cvcvcx.MongoDash.entity.PageSnapshot
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PageSnapshotRepository : MongoRepository<PageSnapshot, String> {
    // 특정 페이지 & 디바이스 타입(PC/Mobile)의 최신 스냅샷 1개 조회
    fun findFirstByPageUrlAndDeviceTypeAndIsActiveTrueOrderByCapturedAtDesc(
        pageUrl: String, 
        deviceType: String
    ): PageSnapshot?
}
