package com.cvcvcx.MongoDash.dto

import jakarta.validation.constraints.NotBlank

data class EventRequest(
    @field:NotBlank
    val userId: String,
    @field:NotBlank
    val eventType: String,
    @field:NotBlank
    val page: String,
    val metadata: Map<String,Any> = emptyMap()
)
