package com.cvcvcx.MongoDash.controller

import com.cvcvcx.MongoDash.dto.ApiResponse
import com.cvcvcx.MongoDash.dto.PageSnapshotDto
import com.cvcvcx.MongoDash.service.PageSnapshotService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/snapshot")
class PageSnapshotController (
    private val snapshotService: PageSnapshotService
) {
    @PostMapping("/upload")
    fun uploadSnapshot(pageSnapshotDto:PageSnapshotDto): ResponseEntity<ApiResponse<String>>{
        val snapshotRequired = snapshotService.isSnapshotRequired(pageSnapshotDto)
        if (snapshotRequired){
            val saveSnapshotUrl = snapshotService.saveSnapshot(pageSnapshotDto)
            return ResponseEntity.ok(ApiResponse(true,"이미지업로드성공",saveSnapshotUrl))
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse(false,"이미지가 이미 존재합니다"))
        }
    }
}