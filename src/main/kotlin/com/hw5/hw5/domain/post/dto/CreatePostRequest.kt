package com.hw5.hw5.domain.post.dto

import java.time.LocalDateTime

data class CreatePostRequest(
    val title: String,
    val content: String,
    val createdAt: LocalDateTime
)
