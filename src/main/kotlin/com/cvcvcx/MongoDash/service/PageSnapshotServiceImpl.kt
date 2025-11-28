package com.cvcvcx.MongoDash.service

import com.cvcvcx.MongoDash.common.ImageUploadService
import com.cvcvcx.MongoDash.dto.PageSnapshotDto
import com.cvcvcx.MongoDash.repository.PageSnapshotRepository
import org.springframework.stereotype.Service

@Service
class PageSnapshotServiceImpl (
    val pageSnapshotRepository: PageSnapshotRepository,
    val imageUploadService: ImageUploadService
) :PageSnapshotService{
    override fun isSnapshotRequired(dto: PageSnapshotDto): Boolean {
        val pageSnapshot =
            pageSnapshotRepository.findFirstByPageUrlAndDeviceTypeAndIsActiveTrueOrderByCapturedAtDesc(
                dto.pageUrl,
                dto.deviceType
            )
        return pageSnapshot == null
    }

    override fun saveSnapshot(dto: PageSnapshotDto): String {

        val uploadImage = imageUploadService.uploadImage(dto.file, "snapshot/")
        val snapshot = dto.toPageSnapshot(uploadImage);
        val saved = pageSnapshotRepository.save(snapshot);
        return saved.pageUrl;
    }
}