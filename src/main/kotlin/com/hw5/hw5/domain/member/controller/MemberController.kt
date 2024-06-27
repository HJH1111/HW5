package com.hw5.hw5.domain.member.controller

import com.hw5.hw5.domain.member.dto.LogInRequest
import com.hw5.hw5.domain.member.dto.LogInResponse
import com.hw5.hw5.domain.member.dto.MemberResponse
import com.hw5.hw5.domain.member.dto.SignUpRequest
import com.hw5.hw5.domain.member.dto.SignUpResponse
import com.hw5.hw5.domain.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getMemberList(): ResponseEntity<List<MemberResponse>> {
        return ResponseEntity.ok(memberService.getMemberList())
    }

    @PostMapping("/signUp")
    fun signUp(
        @Valid
        @RequestBody request: SignUpRequest,
        bindingResult: BindingResult
    ): ResponseEntity<SignUpResponse> {
        if (bindingResult.hasErrors()) {
            throw IllegalArgumentException("닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다")
        }
        return ResponseEntity.ok(memberService.signUp(request))
    }

    @PostMapping("/logIn")
    fun logIn(
        @RequestBody request: LogInRequest
    ): ResponseEntity<LogInResponse> {
        return ResponseEntity.ok(memberService.logIn(request))
    }
}