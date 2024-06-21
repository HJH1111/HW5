package com.hw5.hw5.domain.member.dto

data class MemberResponse(
    val id: Long,
    val email: String,
    val name: String,
    val role: String
)
