package com.hw5.hw5.domain.member.dto

import jakarta.validation.constraints.Pattern

data class SignUpRequest(
    @field:Pattern(
        regexp = "^[a-z0-9]{3,9}+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$",
        message = "@ 앞의 문자열이 최소 3자이상, 9자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성된 이메일이어야 합니다."
    )
    val email: String,

    @field:Pattern(
        regexp = "^[a-zA-Z0-9]{3,}\$\n",
        message = "닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다"
    )
    val name: String,

    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^\\w\\s]).{4,15}$",
        message = "최소 4자 이상, 15자 이하이며 알파벳 대소문자, 숫자, 그리고 특수문자를 포함해야 합니다"
    )
    val password: String,

    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^\\w\\s]).{4,15}$",
        message = "최소 4자 이상, 15자 이하이며 알파벳 대소문자, 숫자, 그리고 특수문자를 포함해야 합니다"
    )
    val passwordCheck: String,

    val role: String
)
