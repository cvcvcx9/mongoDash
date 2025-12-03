package com.cvcvcx.MongoDash.dto

data class HeatmapRequestDto (
    val sessionId: String,
    val pageUrl: String,
    val snapshotWidth: Int,
    val snapshotHeight: Int,
    val deviceType: String
)