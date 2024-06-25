package com.hw5.hw5.domain.comment.dto

import com.hw5.hw5.domain.post.dto.PostResponse
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime
)