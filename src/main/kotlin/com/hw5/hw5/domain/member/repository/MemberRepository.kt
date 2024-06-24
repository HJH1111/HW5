package com.hw5.hw5.domain.member.repository

import com.hw5.hw5.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByEmail(email: String): Member?

    fun existsByEmail(email: String): Boolean

    fun existsByName(name: String): Boolean
}