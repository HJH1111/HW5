package com.hw5.hw5.domain.comment.model

import com.hw5.hw5.domain.comment.dto.CommentResponse
import com.hw5.hw5.domain.member.model.Member
import com.hw5.hw5.domain.post.model.Post
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment(

    @Column(name = "content")
    var content: String,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    var member: Member

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Comment.toCommentResponse(): CommentResponse = CommentResponse(
    id = id!!,
    content = content,
    createdAt = createdAt,

)