package com.hw5.hw5.domain.upvote.dto

import com.hw5.hw5.domain.member.dto.MemberResponse

data class UpvoteResponse(
    val id: Long,
    val upvoteCount: Long,
    val member: MemberResponse,
    val postId: Long
)
