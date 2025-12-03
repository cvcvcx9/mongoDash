package com.cvcvcx.MongoDash.dto

data class HeatmapResponseDto (
    val points: List<HeatmapPoint>,
    val maxValue: Int
)
