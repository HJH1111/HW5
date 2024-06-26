package com.hw5.hw5.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class MemberPrincipal(
    val id: Long,
    val email: String,
    val authorities: Collection<GrantedAuthority>
) {
    constructor(id: Long, email: String, role: Set<String>) : this(
        id,
        email,
        role.map { SimpleGrantedAuthority("ROLE_$it") }
    )
}