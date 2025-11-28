package com.cvcvcx.MongoDash.service

import com.cvcvcx.MongoDash.dto.PageSnapshotDto

interface PageSnapshotService {
//    스냅샷이 필요한지 기기 사이즈, 화면 픽셀 등을 따져서 확인하고, 참, 거짓을 리턴한다.
    fun isSnapshotRequired(dto: PageSnapshotDto): Boolean
//    스냅샷을 레포지토리 정보에 저장한다. 실제 이미지 업로드는 R2에 SDK를 사용하여 업로드
    fun saveSnapshot(dto: PageSnapshotDto): String
}