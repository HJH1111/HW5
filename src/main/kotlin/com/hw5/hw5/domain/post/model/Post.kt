package com.hw5.hw5.domain.post.model

import com.hw5.hw5.domain.member.model.Member
import com.hw5.hw5.domain.member.model.toMemberResponse
import com.hw5.hw5.domain.post.dto.PostResponse
import com.hw5.hw5.domain.upvote.dto.UpvoteResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
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

    @Column(name = "view_count")
    var viewCount: Long = 0,

    @Column(name = "upvote_count")
    var upvoteCount: Long = 0,

    @ManyToOne
    var member: Member
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun viewCount() {
        viewCount += 1
    }

    fun addUpvote() {
        upvoteCount += 1
    }
    fun removeUpvote() {
        upvoteCount -= 1
    }
}

fun Post.toPostResponse(): PostResponse = PostResponse(
    id = id!!,
    title = title,
    content = content
)

fun Post.toUpvoteResponse(member: Member, postId: Long): UpvoteResponse = UpvoteResponse(
    id = id!!,
    upvoteCount = upvoteCount,
    member = member.toMemberResponse(),
    postId = postId
)