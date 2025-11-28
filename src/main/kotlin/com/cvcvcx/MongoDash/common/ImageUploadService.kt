package com.cvcvcx.MongoDash.common

import com.cvcvcx.MongoDash.config.R2Properties
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.UUID
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

private val logger = KotlinLogging.logger {}
@Service
class ImageUploadService (
    private val r2Client:S3Client,
    private val props: R2Properties
){

    fun uploadImage(file:MultipartFile,otherPath:String): String {

        val ext = extractExtension(file.originalFilename)
        val key = "images/${otherPath}${UUID.randomUUID()}.$ext"
        val request = PutObjectRequest.builder()
            .bucket(props.bucket)
            .key(key)
//            다수의 파일을 한 번에 보내는 경우 octet-stream으로 처리되는 것
            .contentType(file.contentType)
            .contentLength(file.size)
            .build()
        logger.info { "엑세스키 확인용 : ${request.key()}" }

        r2Client.putObject(request, RequestBody.fromBytes(file.bytes))
        return buildPublicUrl(key)
    }

    private fun extractExtension(fileName: String?): String =
        fileName?.substringAfterLast('.',"")?.ifBlank { "jpg" }?:"jpg"

    private fun buildPublicUrl(key: String): String =
        "${props.url}/$key"
}