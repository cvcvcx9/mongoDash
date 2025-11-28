package com.cvcvcx.MongoDash.dto

import com.cvcvcx.MongoDash.entity.PageSnapshot
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

data class PageSnapshotDto(
    val pageUrl: String,
    val deviceType: String,
    val width: Int,
    val height: Int,
    val file: MultipartFile
) {
    fun toPageSnapshot(imagePath: String): PageSnapshot {
        return PageSnapshot(
            pageUrl = pageUrl,
            deviceType = deviceType,
            imageWidth = width,
            imageHeight = height,
            imagePath = file.originalFilename.toString(),
            capturedAt = LocalDateTime.now(),
            isActive = true
        )
    }
}
