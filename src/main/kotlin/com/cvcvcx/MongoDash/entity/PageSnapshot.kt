package com.cvcvcx.MongoDash.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "page_snapshots")
open class PageSnapshot(
    @Id
    val id: String? = null,

    @Field("page_url")
    val pageUrl: String,

    @Field("device_type")
    val deviceType: String, // desktop, tablet, mobile

    @Field("image_path")
    val imagePath: String, // S3 URL 또는 로컬 파일 경로

    @Field("image_width")
    val imageWidth: Int,

    @Field("image_height")
    val imageHeight: Int,

    @Field("captured_at")
    val capturedAt: LocalDateTime = LocalDateTime.now(),

    @Field("is_active")
    val isActive: Boolean = true // 최신 버전 여부
)
