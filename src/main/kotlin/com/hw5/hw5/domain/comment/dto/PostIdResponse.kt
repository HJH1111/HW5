package com.hw5.hw5.domain.comment.dto

import java.time.LocalDateTime

data class PostIdResponse(
    val postId: Long,
    val postTitle: String,
    val postContent: String,
    val commentId: Long,
    val commentContent: String,
    val commentCreatedAt: LocalDateTime
)
