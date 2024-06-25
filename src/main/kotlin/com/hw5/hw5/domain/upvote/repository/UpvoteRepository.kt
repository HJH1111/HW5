package com.hw5.hw5.domain.upvote.repository

import com.hw5.hw5.domain.upvote.model.Upvote
import org.springframework.data.jpa.repository.JpaRepository

interface UpvoteRepository : JpaRepository<Upvote, Long> {
    fun existsByMemberIdAndPostId(memberId: Long, postId: Long): Boolean

    fun findByMemberIdAndPostId(memberId: Long, postId: Long): Upvote?
}