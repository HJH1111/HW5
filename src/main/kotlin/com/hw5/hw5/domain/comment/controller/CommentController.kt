package com.hw5.hw5.domain.comment.controller

import com.hw5.hw5.domain.comment.dto.CommentRequest
import com.hw5.hw5.domain.comment.dto.CommentResponse
import com.hw5.hw5.domain.comment.service.CommentService
import com.hw5.hw5.infra.security.MemberPrincipal
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService
) {

    @PreAuthorize("hasRole('ADMIN') or hasRole('GENERAL')")
    @GetMapping
    fun getCommentList(
        @PathVariable postId: Long
    ): ResponseEntity<List<CommentResponse>> {
        return ResponseEntity.ok(commentService.getCommentList(postId))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GENERAL')")
    @GetMapping("/{commentId}")
    fun getComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.getComment(postId, commentId))
    }

    @PreAuthorize("hasRole('GENERAL')")
    @PostMapping
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody request: CommentRequest,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.createComment(postId, request, memberPrincipal))
    }

    @PreAuthorize("hasRole('GENERAL')")
    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentRequest,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, request, memberPrincipal))
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GENERAL')")
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(commentService.deleteComment(postId, commentId, memberPrincipal))
    }
}