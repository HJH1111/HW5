package com.hw5.hw5.domain.post.model

import com.hw5.hw5.domain.post.dto.PostResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post(

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

fun Post.toPostResponse(): PostResponse = PostResponse(
    id = id!!,
    title = title,
    content = content,
    createdAt = createdAt
)