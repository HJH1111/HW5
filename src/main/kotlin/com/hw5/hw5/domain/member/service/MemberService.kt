package com.hw5.hw5.domain.member.service

import com.hw5.hw5.domain.exception.ModelNotFoundException
import com.hw5.hw5.domain.member.dto.LogInRequest
import com.hw5.hw5.domain.member.dto.LogInResponse
import com.hw5.hw5.domain.member.dto.MemberResponse
import com.hw5.hw5.domain.member.dto.SignUpRequest
import com.hw5.hw5.domain.member.dto.SignUpResponse
import com.hw5.hw5.domain.member.model.Member
import com.hw5.hw5.domain.member.model.Role
import com.hw5.hw5.domain.member.model.toMemberResponse
import com.hw5.hw5.domain.member.model.toSignUpResponse
import com.hw5.hw5.domain.member.repository.MemberRepository
import com.hw5.hw5.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val jwtPlugin: JwtPlugin,
    private val passwordEncoder: PasswordEncoder,
) {

    fun getMemberList(): List<MemberResponse> {
        return memberRepository.findAll().map { it.toMemberResponse() }
    }

    fun logIn(request: LogInRequest): LogInResponse {
        val member = memberRepository.findByEmail(request.email) ?: throw ModelNotFoundException("Member", null)

        return LogInResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = member.id.toString(),
                email = member.email,
                role = member.role.name
            )
        )
    }

    fun signUp(request: SignUpRequest): SignUpResponse {
        if (memberRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("해당 이메일 주소는 이미 사용 중입니다. 다른 이메일 주소를 사용해 주세요.")
        }
        if (memberRepository.existsByName(request.name)) {
            throw IllegalArgumentException("이미 다른 계정에서 같은 이름을 사용하고 있습니다. 다른 이름을 사용해 주세요.")
        }
        if (request.name in request.password) throw IllegalArgumentException("비밀번호에 사용자 이름을 사용할 수 없습니다. 다른 비밀번호를 선택해 주세요.")
        if (request.password != request.passwordCheck) throw IllegalArgumentException("비밀번호 불일치")

        return memberRepository.save(
            Member(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name,
                role = when (request.role) {
                    "ADMIN" -> Role.ADMIN
                    "GENERAL" -> Role.GENERAL
                    else -> throw IllegalArgumentException("Invalid Id")
                }
            )
        ).toSignUpResponse()
    }
}