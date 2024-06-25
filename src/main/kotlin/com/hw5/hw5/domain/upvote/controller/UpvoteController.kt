package com.hw5.hw5.domain.upvote.controller

import com.hw5.hw5.domain.upvote.dto.UpvoteResponse
import com.hw5.hw5.domain.upvote.service.UpvoteService
import com.hw5.hw5.infra.security.MemberPrincipal
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts/{postId}/upvote")
class UpvoteController(
    private val upvoteService: UpvoteService
) {

    @PreAuthorize("hasRole('GENERAL')")
    @PostMapping
    fun createUpvote(
        @PathVariable postId: Long,
        @AuthenticationPrincipal member: MemberPrincipal
    ): ResponseEntity<UpvoteResponse> {
        return ResponseEntity.ok(upvoteService.createUpvote(postId, member))
    }

    @PreAuthorize("hasRole('GENERAL')")
    @DeleteMapping
    fun deleteUpvote(
        @PathVariable postId: Long,
        @AuthenticationPrincipal member: MemberPrincipal
    ): ResponseEntity<UpvoteResponse> {
        return ResponseEntity.ok(upvoteService.deleteUpvote(postId, member))
    }
}