package com.hw5.hw5.domain.post.dto

import com.hw5.hw5.domain.comment.dto.CommentResponse
import java.time.LocalDateTime

data class PostResponseWithComment(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val commentList: List<CommentResponse>
)
